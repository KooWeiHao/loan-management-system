package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.exception.BadRequestException;
import eric.koo.loan.management.system.entity.ApplicantEntity;
import eric.koo.loan.management.system.repository.ApplicantRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreditLimitService creditLimitService;

    @Autowired
    ApplicantServiceImpl(ApplicantRepository applicantRepository, PasswordEncoder passwordEncoder, CreditLimitService creditLimitService) {
        this.applicantRepository = applicantRepository;
        this.passwordEncoder = passwordEncoder;
        this.creditLimitService = creditLimitService;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicantEntity> getApplicantByUsername(String username) {
        return applicantRepository.getByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicantEntity> getApplicantByApplicantId(long applicantId) {
        return applicantRepository.findById(applicantId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicantEntity> getApplicantByUsernameAndPassword(String username, String password) {
        return applicantRepository.getByUsername(username)
                .filter(applicant -> passwordEncoder.matches(password, applicant.getPassword()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ApplicantEntity> findAllApplicant() {
        return applicantRepository.findAll();
    }

    @Transactional
    @Override
    public ApplicantEntity createApplicant(String username, String password) {
        var applicant = applicantRepository.getByUsername(username);
        if(applicant.isPresent()) {
            throw new BadRequestException(String.format("Username is registered - %s", username));
        }

        var newApplicant = new ApplicantEntity();
        newApplicant.setUsername(username);
        newApplicant.setPassword(passwordEncoder.encode(password));
        newApplicant.setStatus(ApplicantEntity.Status.PROCESSING);

        return applicantRepository.save(newApplicant);
    }

    @Transactional
    @Override
    public ApplicantEntity approveApplicant(long applicantId, String bankStaff) {
        var applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new BadRequestException(String.format("Invalid applicant - %s", applicantId)));

        if(applicant.getStatus() == ApplicantEntity.Status.APPROVED) {
            throw new BadRequestException(String.format("Applicant has been approved - %s", applicantId));
        }

        var newCreditFacility = new CreditFacilityEntity();
        newCreditFacility.setApplicant(applicant);
        newCreditFacility.setCreditLimit(creditLimitService.getLatestOrDefaultCreditLimit().getCreditLimit());

        applicant.setStatus(ApplicantEntity.Status.APPROVED);
        applicant.setApprovedBy(bankStaff);
        applicant.setApprovedDate(LocalDateTime.now());
        applicant.setCreditFacility(newCreditFacility);

        return applicantRepository.save(applicant);
    }
}
