package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.LoanPaymentEntity;

import java.math.BigDecimal;
import java.util.List;

public interface LoanPaymentService {
    LoanPaymentEntity createLoanPayment(long loanId, BigDecimal amount, String applicantUsername);
    List<LoanPaymentEntity> findLoanPaymentByLoanId(long loanId);
}
