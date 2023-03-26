package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.CreditFacilityCreateResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.CreditFacilityCreateRequestModel;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/credit-facility")
class CreditFacilityController {

    private final CreditFacilityService creditFacilityService;

    private final Validator creditFacilityCreateRequestValidator;

    @Autowired
    CreditFacilityController(CreditFacilityService creditFacilityService, @Qualifier("creditFacilityCreateRequestValidator") Validator creditFacilityCreateRequestValidator) {
        this.creditFacilityService = creditFacilityService;
        this.creditFacilityCreateRequestValidator = creditFacilityCreateRequestValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(creditFacilityCreateRequestValidator);
    }

    @PostMapping
    CreditFacilityCreateResponseModel createCreditFacility(@Valid @RequestBody CreditFacilityCreateRequestModel creditFacilityCreateRequestModel, BindingResult bindingResult, Principal principal) {
        var newCreditFacility = creditFacilityService.createCreditFacility(creditFacilityCreateRequestModel.getCreditLimit(), principal.getName());
        var creditFacilityCreateResponse = new CreditFacilityCreateResponseModel();
        BeanUtils.copyProperties(newCreditFacility, creditFacilityCreateResponse);

        return creditFacilityCreateResponse;
    }
}
