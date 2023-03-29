package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.ApplicantResponseModel;
import eric.koo.loan.management.system.entity.ApplicantEntity;
import eric.koo.loan.management.system.service.ApplicantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return createApplicantResponse(applicant);
    }

    @GetMapping
    ApplicantResponseModel getApplicant(Principal principal) {
        var applicant = applicantService.getApplicantByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Invalid applicant - %s", principal.getName())));

        return createApplicantResponse(applicant);
    }

    @GetMapping("/all")
    List<ApplicantResponseModel> getAllApplicant() {
        var applicants = applicantService.findAllApplicant();

        return applicants.stream()
                .map(this::createApplicantResponse)
                .collect(Collectors.toList());
    }

    private ApplicantResponseModel createApplicantResponse(ApplicantEntity applicant) {
        var applicantResponse = new ApplicantResponseModel();
        BeanUtils.copyProperties(applicant, applicantResponse);

        Optional.ofNullable(applicant.getCreditFacility())
                .ifPresent(creditFacility -> {
                    var creditFacilityModel = new ApplicantResponseModel.CreditFacilityModel();
                    BeanUtils.copyProperties(creditFacility, creditFacilityModel);
                    applicantResponse.setCreditFacility(creditFacilityModel);
                });

        return applicantResponse;
    }
}
