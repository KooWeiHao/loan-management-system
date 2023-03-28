package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.LoanPaymentEntity;

import java.math.BigDecimal;

public interface LoanPaymentService {
    LoanPaymentEntity createLoanPayment(long loanId, BigDecimal amount, String applicantUsername);
}
