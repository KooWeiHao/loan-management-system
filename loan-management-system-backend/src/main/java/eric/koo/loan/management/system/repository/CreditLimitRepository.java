package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.CreditLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditLimitRepository extends JpaRepository<CreditLimitEntity, Long> {
    Optional<CreditLimitEntity> getFirstByOrderByCreditLimitDateDesc();
}
