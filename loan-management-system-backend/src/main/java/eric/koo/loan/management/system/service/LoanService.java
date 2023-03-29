package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import eric.koo.loan.management.system.entity.LoanEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoanService {
    Optional<LoanEntity> getLoanByLoanId(long loanId);
    List<LoanEntity> findLoanByApplicantUsername(String applicantUsername);
    List<LoanEntity> findAllLoan();

    LoanEntity createLoan(BigDecimal amount, LoanEntity.Type type, InterestRateEntity.Type paymentType, String applicantUsername);
    LoanEntity approveLoan(long loanId, String bankStaff);
}
