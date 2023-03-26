package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.config.LoanManagementSystemException;
import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.repository.CreditFacilityRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
class CreditFacilityServiceImpl implements CreditFacilityService {

    private final CreditFacilityRepository creditFacilityRepository;
    private final ApplicantService applicantService;

    @Autowired
    public CreditFacilityServiceImpl(CreditFacilityRepository creditFacilityRepository, ApplicantService applicantService) {
        this.creditFacilityRepository = creditFacilityRepository;
        this.applicantService = applicantService;
    }

    @Transactional
    @Override
    public CreditFacilityEntity createCreditFacility(BigDecimal creditLimit, String applicantUsername) {
        var applicant = applicantService.getByUsername(applicantUsername)
                .orElseThrow(() -> new LoanManagementSystemException(String.format("Invalid applicant - %s", applicantUsername)));

        var newCreditFacility = new CreditFacilityEntity();
        newCreditFacility.setCreditLimit(creditLimit);
        newCreditFacility.setApplicant(applicant);
        newCreditFacility.setStatus(CreditFacilityEntity.Status.PENDING);

        return creditFacilityRepository.save(newCreditFacility);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CreditFacilityEntity> getCreditFacilityByApplicantUsername(String applicantUsername) {
        return creditFacilityRepository.getByApplicantUsername(applicantUsername);
    }
}
