package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRateEntity, Long> {
    Optional<InterestRateEntity> getFirstByTypeAndInterestRateDateLessThanEqualOrderByInterestRateDateDescCreatedDateDesc(InterestRateEntity.Type type, LocalDate data);
}
