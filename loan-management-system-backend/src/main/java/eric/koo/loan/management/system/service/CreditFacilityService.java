package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;

import java.math.BigDecimal;

public interface CreditFacilityService {
    CreditFacilityEntity createCreditFacility(BigDecimal creditLimit, String applicantUsername);
    CreditFacilityEntity approveCreditFacility(long creditFacilityId, String bankStaff);
}
