package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.LoanEntity;
import eric.koo.loan.management.system.entity.LoanPaymentEntity;
import eric.koo.loan.management.system.exception.BadRequestException;
import eric.koo.loan.management.system.repository.LoanPaymentRepository;
import eric.koo.loan.management.system.service.LoanPaymentService;
import eric.koo.loan.management.system.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
class LoanPaymentServiceImpl implements LoanPaymentService {

    private final LoanPaymentRepository loanPaymentRepository;
    private final LoanService loanService;

    @Autowired
    LoanPaymentServiceImpl(LoanPaymentRepository loanPaymentRepository, LoanService loanService) {
        this.loanPaymentRepository = loanPaymentRepository;
        this.loanService = loanService;
    }

    @Transactional
    @Override
    public LoanPaymentEntity createLoanPayment(long loanId, BigDecimal amount, String applicantUsername) {
        var loan = loanService.getLoanByLoanId(loanId)
                .orElseThrow(() -> new BadRequestException(String.format("Invalid loan - %s", loanId)));

        if(loan.getStatus() != LoanEntity.Status.ACTIVE) {
            throw new BadRequestException(String.format("Loan is not active - %s", loan.getStatus()));
        }

        if(!loan.getCreditFacility().getApplicant().getUsername().equals(applicantUsername)) {
            throw new BadRequestException(String.format("Wrong applicant - %s", applicantUsername));
        }

        var repaymentAmount = loan.getPrincipalAmount()
                .add(loan.getPrincipalAmount().multiply(loan.getInterestRate()))
                .setScale(2, RoundingMode.HALF_UP);
        var totalCurrentPaidAmount = loanPaymentRepository.findByLoanLoanId(loanId).stream()
                .map(LoanPaymentEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalNewPaidAmount = totalCurrentPaidAmount.add(amount);

        if(repaymentAmount.compareTo(totalNewPaidAmount) < 0) {
            throw new BadRequestException(String.format(
               "Total paid amount is greater than repayment amount - {repaymentAmount: %s, currentPaidAmount: %s, newPaidAmount: %s}",
               repaymentAmount, totalCurrentPaidAmount, totalNewPaidAmount
            ));
        }

        var loanPayment = new LoanPaymentEntity();
        loanPayment.setLoan(loan);
        loanPayment.setAmount(amount);
        loanPaymentRepository.save(loanPayment);

        if(repaymentAmount.compareTo(totalNewPaidAmount) == 0) {
            loan.setStatus(LoanEntity.Status.PAID);
        }

        return loanPayment;
    }
}
