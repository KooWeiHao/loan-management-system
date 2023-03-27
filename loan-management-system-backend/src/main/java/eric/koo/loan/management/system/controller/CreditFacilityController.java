package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.CreditFacilityResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.CreditFacilityApproveRequestModel;
import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/credit-facility")
class CreditFacilityController {

    private final CreditFacilityService creditFacilityService;

    @Autowired
    CreditFacilityController(CreditFacilityService creditFacilityService) {
        this.creditFacilityService = creditFacilityService;
    }

    @PostMapping
    CreditFacilityResponseModel getOrCreateCreditFacility(Principal principal) {
        var creditFacility = creditFacilityService.getOrCreateCreditFacility(principal.getName());

        return createCreditFacilityResponseModel(creditFacility);
    }

    @PostMapping("/approve")
    CreditFacilityResponseModel approveCreditFacility(@Valid @RequestBody CreditFacilityApproveRequestModel creditFacilityApproveRequestModel, Principal principal) {
        var approvedCreditFacility = creditFacilityService.approveCreditFacility(creditFacilityApproveRequestModel.getCreditFacilityId(), creditFacilityApproveRequestModel.getCreditLimit(), principal.getName());

        return createCreditFacilityResponseModel(approvedCreditFacility);
    }

    @GetMapping
    List<CreditFacilityResponseModel> findAllCreditFacility() {
        var creditFacilities = creditFacilityService.findAllCreditFacility();

        return creditFacilities.stream()
                .map(this::createCreditFacilityResponseModel)
                .collect(Collectors.toList());
    }

    private CreditFacilityResponseModel createCreditFacilityResponseModel(CreditFacilityEntity creditFacility) {
        var creditFacilityResponse = new CreditFacilityResponseModel();
        BeanUtils.copyProperties(creditFacility, creditFacilityResponse);
        creditFacilityResponse.setApplicantUsername(creditFacility.getApplicant().getUsername());

        return creditFacilityResponse;
    }
}
