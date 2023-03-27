package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditLimitEntity;

public interface CreditLimitService {
    CreditLimitEntity getLatestOrDefaultCreditLimit();
}
