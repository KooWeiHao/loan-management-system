package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.LoanPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPaymentEntity, Long> {
    List<LoanPaymentEntity> findByLoanLoanId(long loanId);
}
