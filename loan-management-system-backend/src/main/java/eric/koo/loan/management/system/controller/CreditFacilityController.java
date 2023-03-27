package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.CreditFacilityResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.CreditFacilityCreateRequestModel;
import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    CreditFacilityResponseModel createCreditFacility(@Valid @RequestBody CreditFacilityCreateRequestModel creditFacilityCreateRequestModel, Principal principal) {
        var newCreditFacility = creditFacilityService.createCreditFacility(creditFacilityCreateRequestModel.getCreditLimit(), principal.getName());

        return createCreditFacilityResponseModel(newCreditFacility);
    }

    @PostMapping("/approve")
    CreditFacilityResponseModel approveCreditFacility(@RequestParam("creditFacilityId") Long creditFacilityId, Principal principal) {
        var approvedCreditFacility = creditFacilityService.approveCreditFacility(creditFacilityId, principal.getName());

        return createCreditFacilityResponseModel(approvedCreditFacility);
    }

    private CreditFacilityResponseModel createCreditFacilityResponseModel(CreditFacilityEntity creditFacility) {
        var creditFacilityResponse = new CreditFacilityResponseModel();
        BeanUtils.copyProperties(creditFacility, creditFacilityResponse);

        return creditFacilityResponse;
    }
}
