package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.exception.BadRequestException;
import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.repository.CreditFacilityRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
    public CreditFacilityEntity getOrCreateCreditFacility(String applicantUsername) {
        var applicant = applicantService.getApplicantByUsername(applicantUsername)
                .orElseThrow(() -> new BadRequestException(String.format("Invalid applicant - %s", applicantUsername)));

        return creditFacilityRepository.getByApplicantUsername(applicant.getUsername())
                .orElseGet(() -> {
                    var newCreditFacility = new CreditFacilityEntity();
                    newCreditFacility.setApplicant(applicant);
                    return creditFacilityRepository.save(newCreditFacility);
                });
    }

    @Transactional
    @Override
    public CreditFacilityEntity approveCreditFacility(long creditFacilityId, BigDecimal creditLimit, String bankStaff) {
        var creditFacility = creditFacilityRepository.findById(creditFacilityId)
                .orElseThrow(() -> new BadRequestException(String.format("Invalid credit facility - %s", creditFacilityId)));

        creditFacility.setCreditLimit(creditLimit);

        return creditFacilityRepository.save(creditFacility);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CreditFacilityEntity> findAllCreditFacility() {
        return creditFacilityRepository.findAll();
    }
}
