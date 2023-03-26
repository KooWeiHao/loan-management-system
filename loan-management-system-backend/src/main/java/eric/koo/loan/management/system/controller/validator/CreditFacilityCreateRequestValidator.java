package eric.koo.loan.management.system.controller.validator;

import eric.koo.loan.management.system.controller.model.resquest.CreditFacilityCreateRequestModel;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
class CreditFacilityCreateRequestValidator implements Validator {

    private final CreditFacilityService creditFacilityService;

    @Autowired
    CreditFacilityCreateRequestValidator(CreditFacilityService creditFacilityService) {
        this.creditFacilityService = creditFacilityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof CreditFacilityCreateRequestModel) {
            var applicantUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            var creditFacility = creditFacilityService.getCreditFacilityByApplicantUsername(applicantUsername);

            if(creditFacility.isPresent()) {
                errors.reject("error", String.format("Credit facility is opened for %s", applicantUsername));
            }
        }
    }
}
