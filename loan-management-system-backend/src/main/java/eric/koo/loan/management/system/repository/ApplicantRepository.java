package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long> {
    Optional<ApplicantEntity> getByUsername(String username);
}
