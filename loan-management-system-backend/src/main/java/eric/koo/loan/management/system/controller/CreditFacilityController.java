package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.CreditFacilityResponseModel;
import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
