package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface CreditFacilityService {
    CreditFacilityEntity createCreditFacility(BigDecimal creditLimit, String applicantUsername);
    Optional<CreditFacilityEntity> getCreditFacilityByApplicantUsername(String applicantUsername);
}
