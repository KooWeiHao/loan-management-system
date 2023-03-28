package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import eric.koo.loan.management.system.entity.LoanEntity;
import eric.koo.loan.management.system.exception.BadRequestException;
import eric.koo.loan.management.system.repository.LoanRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditFacilityService;
import eric.koo.loan.management.system.service.InterestRateService;
import eric.koo.loan.management.system.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final ApplicantService applicantService;
    private final CreditFacilityService creditFacilityService;
    private final InterestRateService interestRateService;

    @Autowired
    LoanServiceImpl(LoanRepository loanRepository, ApplicantService applicantService, CreditFacilityService creditFacilityService, InterestRateService interestRateService) {
        this.loanRepository = loanRepository;
        this.applicantService = applicantService;
        this.creditFacilityService = creditFacilityService;
        this.interestRateService = interestRateService;
    }

    @Transactional
    @Override
    public LoanEntity createLoan(BigDecimal amount, LoanEntity.Type type, InterestRateEntity.Type paymentType, String applicantUsername) {
        var applicant = applicantService.getApplicantByUsername(applicantUsername)
                .orElseThrow(() -> new BadRequestException(String.format("Invalid applicant - %s", applicantUsername)));

        var creditFacility = creditFacilityService.getCreditFacilityByApplicantUsername(applicant.getUsername())
                .orElseThrow(() -> new BadRequestException(String.format("Credit facility not found - %s", applicant.getUsername())));

        var totalExistingLoanPrincipalAmount = loanRepository.findByCreditFacilityCreditFacilityId(creditFacility.getCreditFacilityId()).stream()
                .map(LoanEntity::getPrincipalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(creditFacility.getCreditLimit()
                .compareTo(totalExistingLoanPrincipalAmount.add(amount)) < 0
        ) {
            throw new BadRequestException(String.format(
                    "Total principal amount is greater than credit limit - {creditLimit: %s, existingPrincipalAmount: %s, requestedAmount: %s}",
                    creditFacility.getCreditLimit(),
                    totalExistingLoanPrincipalAmount,
                    amount
            ));
        }

        var interestRate = interestRateService.getLatestOrDefaultInterestRateByType(paymentType).getInterestRate();

        var firstPaymentDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusMonths(1);

        var lastPaymentDate = LocalDateTime.of(firstPaymentDate.toLocalDate(), LocalTime.MAX);
        switch (paymentType) {
            case PARTIAL_PAYMENT_ONE_YEAR:
                lastPaymentDate = lastPaymentDate.plusYears(1);
                break;

            case PARTIAL_PAYMENT_TWO_YEARS:
                lastPaymentDate = lastPaymentDate.plusYears(2);
                break;

            case FULL_PAYMENT:
            default:
                break;
        }

        var newLoan = new LoanEntity();
        newLoan.setCreditFacility(creditFacility);
        newLoan.setType(type);
        newLoan.setPrincipalAmount(amount);
        newLoan.setInterestRate(interestRate);
        newLoan.setStatus(LoanEntity.Status.PROCESSING);
        newLoan.setFirstPaymentDate(firstPaymentDate);
        newLoan.setLastPaymentDate(lastPaymentDate);

        return loanRepository.save(newLoan);
    }
}
