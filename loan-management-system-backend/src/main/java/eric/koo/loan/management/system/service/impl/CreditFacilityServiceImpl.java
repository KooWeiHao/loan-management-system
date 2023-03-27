package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.ApplicantEntity;
import eric.koo.loan.management.system.exception.BadRequestException;
import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.repository.CreditFacilityRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditFacilityService;
import eric.koo.loan.management.system.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class CreditFacilityServiceImpl implements CreditFacilityService {

    private final CreditFacilityRepository creditFacilityRepository;
    private final ApplicantService applicantService;
    private final CreditLimitService creditLimitService;

    @Autowired
    public CreditFacilityServiceImpl(CreditFacilityRepository creditFacilityRepository, ApplicantService applicantService, CreditLimitService creditLimitService) {
        this.creditFacilityRepository = creditFacilityRepository;
        this.applicantService = applicantService;
        this.creditLimitService = creditLimitService;
    }

    @Transactional
    @Override
    public CreditFacilityEntity createCreditFacility(long applicantId, String bankStaff) {
        var applicant = applicantService.getApplicantByApplicantId(applicantId)
                .orElseThrow(() -> new BadRequestException(String.format("Invalid applicant - %s", applicantId)));

        if(applicant.getStatus() != ApplicantEntity.Status.APPROVED) {
            throw new BadRequestException(String.format("Only approved applicant can open a credit facility - %s", applicantId));
        }

        var newCreditFacility = new CreditFacilityEntity();
        newCreditFacility.setApplicant(applicant);
        newCreditFacility.setCreditLimit(creditLimitService.getLatestOrDefaultCreditLimit().getCreditLimit());

        return newCreditFacility;
    }
}
