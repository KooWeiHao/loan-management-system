package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditFacilityRepository extends JpaRepository<CreditFacilityEntity, Long> {
    Optional<CreditFacilityEntity> getByApplicantUsername(String applicantUsername);
}
