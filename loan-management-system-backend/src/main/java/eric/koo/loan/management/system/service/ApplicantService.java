package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.ApplicantEntity;

import java.util.Optional;

public interface ApplicantService {
    Optional<ApplicantEntity> getApplicantByApplicantId(long applicantId);
    Optional<ApplicantEntity> getApplicantByUsernameAndPassword(String username, String password);

    ApplicantEntity createApplicant(String username, String password);
    ApplicantEntity approveApplicant(long applicantId, String bankStaff);
}
