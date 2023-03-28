package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditLimitEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CreditLimitService {
    CreditLimitEntity getLatestOrDefaultCreditLimit();
    CreditLimitEntity createCreditLimit(BigDecimal creditLimit, LocalDate creditLimitDate);
}
