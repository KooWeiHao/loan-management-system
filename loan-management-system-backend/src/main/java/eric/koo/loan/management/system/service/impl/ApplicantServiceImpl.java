package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.exception.LoanManagementSystemException;
import eric.koo.loan.management.system.entity.ApplicantEntity;
import eric.koo.loan.management.system.repository.ApplicantRepository;
import eric.koo.loan.management.system.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<ApplicantEntity> getApplicantByUsernameAndPassword(String username, String password) {
        return applicantRepository.getByUsername(username)
                .filter(applicant -> passwordEncoder.matches(password, applicant.getPassword()));
    }

    @Transactional
    @Override
    public ApplicantEntity createApplicant(String username, String password) {
        var applicant = applicantRepository.getByUsername(username);
        if(applicant.isPresent()) {
            throw new LoanManagementSystemException(String.format("Username is registered - %s", username));
        }

        var newApplicant = new ApplicantEntity();
        newApplicant.setUsername(username);
        newApplicant.setPassword(passwordEncoder.encode(password));

        return applicantRepository.save(newApplicant);
    }
}
