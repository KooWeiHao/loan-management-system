package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.CreditFacilityCreateResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.CreditFacilityCreateRequestModel;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CreditFacilityController(CreditFacilityService creditFacilityService) {
        this.creditFacilityService = creditFacilityService;
    }

    @PostMapping
    CreditFacilityCreateResponseModel createCreditFacility(@Valid @RequestBody CreditFacilityCreateRequestModel creditFacilityCreateRequestModel, Principal principal) {
        var newCreditFacility = creditFacilityService.createCreditFacility(creditFacilityCreateRequestModel.getCreditLimit(), principal.getName());

        var creditFacilityCreateResponse = new CreditFacilityCreateResponseModel();
        BeanUtils.copyProperties(newCreditFacility, creditFacilityCreateResponse);

        return creditFacilityCreateResponse;
    }
}
