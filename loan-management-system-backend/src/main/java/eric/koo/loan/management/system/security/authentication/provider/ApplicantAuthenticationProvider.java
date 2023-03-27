package eric.koo.loan.management.system.security.authentication.provider;

import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ApplicantAuthenticationProvider extends AbstractAuthenticationProvider {

    private final ApplicantService applicantService;

    @Autowired
    ApplicantAuthenticationProvider(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Override
    boolean isValid(String username, String password) {
        return applicantService.getApplicantByUsernameAndPassword(username, password)
                .isPresent();
    }

    @Override
    Role role() {
        return Role.APPLICANT;
    }
}
