package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditFacilityRepository extends JpaRepository<CreditFacilityEntity, Long> {
    Optional<CreditFacilityEntity> getByApplicantUsername(String applicantUsername);
}
