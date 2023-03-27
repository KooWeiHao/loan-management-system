package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.AuthResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoginRequestModel;
import eric.koo.loan.management.system.controller.model.resquest.RegisterRequestModel;
import eric.koo.loan.management.system.entity.CreditLimitEntity;
import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.BankStaffService;
import eric.koo.loan.management.system.service.ApplicantService;
import eric.koo.loan.management.system.service.CreditLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
class AuthController {

    private final ApplicantService applicantService;
    private final BankStaffService bankStaffService;

    @Autowired
    AuthController(ApplicantService applicantService, BankStaffService bankStaffService) {
        this.applicantService = applicantService;
        this.bankStaffService = bankStaffService;
    }

    @PostMapping("/register")
    AuthResponseModel register(@Valid @RequestBody RegisterRequestModel registerRequestModel) {
        var newApplicant = applicantService.createApplicant(registerRequestModel.getUsername(), registerRequestModel.getPassword());

        return createAuthResponseModel(newApplicant.getUsername(), Role.APPLICANT);
    }

    @PostMapping("/login")
    AuthResponseModel login(@Valid @RequestBody LoginRequestModel loginRequestModel) {
        var applicant = applicantService.getApplicantByUsernameAndPassword(loginRequestModel.getUsername(), loginRequestModel.getPassword());

        if(applicant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Invalid Credentials - %s", loginRequestModel.getUsername()));
        }

        return createAuthResponseModel(applicant.get().getUsername(), Role.APPLICANT);
    }

    @PostMapping("/login-as-bank-staff")
    AuthResponseModel loginAsBankStaff(@Valid @RequestBody LoginRequestModel loginRequestModel) {
        var isBankStaff = bankStaffService.validateBankStaffUsernameAndPassword(loginRequestModel.getUsername(), loginRequestModel.getPassword());

        if(!isBankStaff) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Invalid Bank Staff Credentials - %s", loginRequestModel.getUsername()));
        }

        return createAuthResponseModel(loginRequestModel.getUsername(), Role.BANK_STAFF);
    }

    @Autowired
    private CreditLimitService creditLimitService;

    @GetMapping("/test")
    CreditLimitEntity get(){
        return creditLimitService.getLatestOrDefaultCreditLimit();
    }

    private AuthResponseModel createAuthResponseModel(String username, Role role) {
        var authResponse = new AuthResponseModel();
        authResponse.setUsername(username);
        authResponse.setRole(role);

        return authResponse;
    }
}
