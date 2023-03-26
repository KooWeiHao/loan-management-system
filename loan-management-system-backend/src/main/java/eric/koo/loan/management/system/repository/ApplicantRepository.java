package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long> {
    Optional<ApplicantEntity> getByUsername(String username);
}
