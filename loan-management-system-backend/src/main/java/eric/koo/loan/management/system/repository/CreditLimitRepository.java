package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.CreditLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditLimitRepository extends JpaRepository<CreditLimitEntity, Long> {
    Optional<CreditLimitEntity> getFirstByCreditLimitDateLessThanEqualOrderByCreditLimitDateDescCreatedDateDesc(LocalDate date);
    List<CreditLimitEntity> findAllByOrderByCreditLimitDateDescCreatedDateDesc();
}
