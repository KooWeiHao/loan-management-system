package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import eric.koo.loan.management.system.entity.LoanEntity;
import eric.koo.loan.management.system.repository.LoanRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditFacilityService;
import eric.koo.loan.management.system.service.InterestRateService;
import eric.koo.loan.management.system.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
                .orElseThrow(() -> new BadCredentialsException(String.format("Invalid applicant - %s", applicantUsername)));

        var creditFacility = creditFacilityService.getCreditFacilityByApplicantUsername(applicant.getUsername())
                .orElseThrow(() -> new BadCredentialsException(String.format("Credit facility not found - %s", applicant.getUsername())));

        //TODO - validate the amount
        var loan = loanRepository.findByCreditFacilityCreditFacilityId(creditFacility.getCreditFacilityId())
                .stream().map(a -> a.getPrincipal());

        var interestRate = interestRateService.getLatestOrDefaultInterestRateByType(paymentType).getInterestRate();

        var firstPaymentDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusMonths(1);

        var lastPaymentDate = LocalDateTime.of(firstPaymentDate.toLocalDate(), LocalTime.MAX);
        switch (paymentType) {
            case PARTIAL_PAYMENT_ONE_YEAR:
                lastPaymentDate = lastPaymentDate.plusYears(1);

            case PARTIAL_PAYMENT_TWO_YEARS:
                lastPaymentDate = lastPaymentDate.plusYears(2);
        }

        var newLoan = new LoanEntity();
        newLoan.setCreditFacility(creditFacility);
        newLoan.setType(type);
        newLoan.setPrincipal(amount);
        newLoan.setInterestRate(interestRate);
        newLoan.setStatus(LoanEntity.Status.PROCESSING);
        newLoan.setFirstPaymentDate(firstPaymentDate);
        newLoan.setLastPaymentDate(lastPaymentDate);

        return loanRepository.save(newLoan);
    }
}
