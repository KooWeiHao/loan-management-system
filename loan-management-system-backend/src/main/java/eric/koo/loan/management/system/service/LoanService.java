package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import eric.koo.loan.management.system.entity.LoanEntity;

import java.math.BigDecimal;

public interface LoanService {
    LoanEntity createLoan(BigDecimal amount, LoanEntity.Type type, InterestRateEntity.Type paymentType, String applicantUsername);
    LoanEntity approveLoan(long loanId, String bankStaff);
}
