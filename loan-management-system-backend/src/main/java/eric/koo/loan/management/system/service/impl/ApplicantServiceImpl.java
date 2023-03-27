package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.exception.BadRequestException;
import eric.koo.loan.management.system.entity.ApplicantEntity;
import eric.koo.loan.management.system.repository.ApplicantRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    ApplicantServiceImpl(ApplicantRepository applicantRepository, PasswordEncoder passwordEncoder) {
        this.applicantRepository = applicantRepository;
        this.passwordEncoder = passwordEncoder;
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

        applicant.setStatus(ApplicantEntity.Status.APPROVED);
        applicant.setApprovedBy(bankStaff);
        applicant.setApprovedDate(LocalDateTime.now());

        return applicantRepository.save(applicant);
    }
}
