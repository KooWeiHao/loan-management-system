package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditFacilityService;
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

    // TODO - Design the response model
    @PostMapping
    void approveApplicant(@RequestParam("applicantId") Long applicantId, Principal principal) {
        applicantService.approveApplicant(applicantId, principal.getName());
        creditFacilityService.createCreditFacility(applicantId, principal.getName());
    }
}
