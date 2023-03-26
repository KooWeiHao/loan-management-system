package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.ApplicantEntity;

import java.util.Optional;

public interface ApplicantService {
    Optional<ApplicantEntity> getByUsername(String username);
    Optional<ApplicantEntity> getByUsernameAndPassword(String username, String password);
    ApplicantEntity createApplicant(String username, String password);
}
