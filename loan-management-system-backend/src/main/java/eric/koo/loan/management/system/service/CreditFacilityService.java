package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;

import java.util.Optional;

public interface CreditFacilityService {
    CreditFacilityEntity createCreditFacility(long applicantId, String bankStaff);
    Optional<CreditFacilityEntity> getCreditFacilityByApplicantUsername(String applicantUsername);
}
