package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;

import java.util.List;

public interface CreditFacilityService {
    CreditFacilityEntity createCreditFacility(long applicantId, String bankStaff);
}
