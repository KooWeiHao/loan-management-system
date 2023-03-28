package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.repository.CreditFacilityRepository;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class CreditFacilityServiceImpl implements CreditFacilityService {

    private final CreditFacilityRepository creditFacilityRepository;

    @Autowired
    public CreditFacilityServiceImpl(CreditFacilityRepository creditFacilityRepository) {
        this.creditFacilityRepository = creditFacilityRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CreditFacilityEntity> getCreditFacilityByApplicantUsername(String applicantUsername) {
        return creditFacilityRepository.getByApplicantUsername(applicantUsername);
    }
}
