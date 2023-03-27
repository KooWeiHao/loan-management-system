package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;

import java.math.BigDecimal;
import java.util.List;

public interface CreditFacilityService {
    CreditFacilityEntity getOrCreateCreditFacility(String applicantUsername);
    CreditFacilityEntity approveCreditFacility(long creditFacilityId, BigDecimal creditLimit, String bankStaff);
    List<CreditFacilityEntity> findAllCreditFacility();
}
