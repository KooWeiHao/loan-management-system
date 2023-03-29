package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditLimitEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CreditLimitService {
    CreditLimitEntity getDefaultCreditLimit();
    CreditLimitEntity getLatestOrDefaultCreditLimit();
    List<CreditLimitEntity> findAllCreditLimit();
    CreditLimitEntity createCreditLimit(BigDecimal creditLimit, LocalDate creditLimitDate);
}
