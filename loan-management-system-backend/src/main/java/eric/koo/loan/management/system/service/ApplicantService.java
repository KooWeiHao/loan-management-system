package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.ApplicantEntity;

import java.util.Optional;

public interface ApplicantService {
    Optional<ApplicantEntity> getApplicantByUsername(String username);
    Optional<ApplicantEntity> getApplicantByUsernameAndPassword(String username, String password);
    ApplicantEntity createApplicant(String username, String password);
}
