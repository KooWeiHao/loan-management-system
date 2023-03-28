package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;

import java.util.Optional;

public interface CreditFacilityService {
    Optional<CreditFacilityEntity> getCreditFacilityByApplicantUsername(String applicantUsername);
}
