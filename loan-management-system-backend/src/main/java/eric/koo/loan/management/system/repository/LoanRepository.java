package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByCreditFacilityCreditFacilityId(long creditFacilityId);
}
