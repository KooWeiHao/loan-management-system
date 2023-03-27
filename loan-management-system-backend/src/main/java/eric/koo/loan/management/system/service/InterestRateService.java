package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.InterestRateEntity;

public interface InterestRateService {
    InterestRateEntity getLatestOrDefaultInterestRateByType(InterestRateEntity.Type type);
}
