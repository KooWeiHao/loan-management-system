package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.ApplicantResponseModel;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditFacilityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/applicant")
class ApplicantController {

    private final ApplicantService applicantService;
    private final CreditFacilityService creditFacilityService;

    @Autowired
    ApplicantController(ApplicantService applicantService, CreditFacilityService creditFacilityService) {
        this.applicantService = applicantService;
        this.creditFacilityService = creditFacilityService;
    }

    @PostMapping("/approve")
    ApplicantResponseModel approveApplicant(@RequestParam("applicantId") Long applicantId, Principal principal) {
        var applicant = applicantService.approveApplicant(applicantId, principal.getName());
        var creditFacility = creditFacilityService.createCreditFacility(applicantId, principal.getName());

        var applicantResponse = new ApplicantResponseModel();
        BeanUtils.copyProperties(applicant, applicantResponse);
        applicantResponse.setCreditFacilityId(creditFacility.getCreditFacilityId());

        return applicantResponse;
    }
}
