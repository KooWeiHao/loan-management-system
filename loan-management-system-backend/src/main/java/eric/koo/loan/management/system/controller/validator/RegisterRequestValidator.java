package eric.koo.loan.management.system.controller.validator;

import eric.koo.loan.management.system.controller.model.resquest.RegisterRequestModel;
import eric.koo.loan.management.system.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
class RegisterRequestValidator implements Validator {

    private final ApplicantService applicantService;

    @Autowired
    public RegisterRequestValidator(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof RegisterRequestModel) {
            var registerRequest = (RegisterRequestModel) target;
            var applicant = applicantService.getApplicantByUsername(registerRequest.getUsername());

            if(applicant.isPresent()) {
                errors.reject("error", String.format("Username is registered - %s", registerRequest.getUsername()));
            }
        }
    }
}
