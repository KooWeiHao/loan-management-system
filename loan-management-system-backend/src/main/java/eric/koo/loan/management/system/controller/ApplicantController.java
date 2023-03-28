package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.ApplicantResponseModel;
import eric.koo.loan.management.system.service.ApplicantService;
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

    @Autowired
    ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping("/approve")
    ApplicantResponseModel approveApplicant(@RequestParam("applicantId") Long applicantId, Principal principal) {
        var applicant = applicantService.approveApplicant(applicantId, principal.getName());

        var applicantResponse = new ApplicantResponseModel();
        BeanUtils.copyProperties(applicant, applicantResponse);
        applicantResponse.setCreditFacilityId(applicant.getCreditFacility().getCreditFacilityId());

        return applicantResponse;
    }
}
