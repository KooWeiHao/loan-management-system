package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.InterestRateEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InterestRateService {
    InterestRateEntity getLatestOrDefaultInterestRateByType(InterestRateEntity.Type type);
    InterestRateEntity createInterestRate(BigDecimal interestRateInPercentage, LocalDate interestRateDate, InterestRateEntity.Type type);
}
