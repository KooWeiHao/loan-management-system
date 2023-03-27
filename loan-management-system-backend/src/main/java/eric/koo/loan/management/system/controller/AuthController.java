package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.AuthResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoginRequestModel;
import eric.koo.loan.management.system.controller.model.resquest.RegisterRequestModel;
import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.BankStaffService;
import eric.koo.loan.management.system.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
class AuthController {

    private final ApplicantService applicantService;
    private final BankStaffService bankStaffService;

    private final Validator registerRequestValidator;

    @Autowired
    AuthController(ApplicantService applicantService, BankStaffService bankStaffService, @Qualifier("registerRequestValidator") Validator registerRequestValidator) {
        this.applicantService = applicantService;
        this.bankStaffService = bankStaffService;
        this.registerRequestValidator = registerRequestValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registerRequestValidator);
    }

    @PostMapping("/register")
    AuthResponseModel register(@Valid @RequestBody RegisterRequestModel registerRequestModel, BindingResult bindingResult) {
        var newApplicant = applicantService.createApplicant(registerRequestModel.getUsername(), registerRequestModel.getPassword());

        return createAuthResponseModel(newApplicant.getUsername(), Role.APPLICANT);
    }

    @PostMapping("/login")
    AuthResponseModel login(@Valid @RequestBody LoginRequestModel loginRequestModel, BindingResult bindingResult) {
        var applicant = applicantService.getApplicantByUsernameAndPassword(loginRequestModel.getUsername(), loginRequestModel.getPassword());

        if(applicant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Invalid Credentials - %s", loginRequestModel.getUsername()));
        }

        return createAuthResponseModel(applicant.get().getUsername(), Role.APPLICANT);
    }

    @PostMapping("/login-as-bank-staff")
    AuthResponseModel loginAsBankStaff(@Valid @RequestBody LoginRequestModel loginRequestModel, BindingResult bindingResult) {
        var isBankStaff = bankStaffService.validateBankStaffUsernameAndPassword(loginRequestModel.getUsername(), loginRequestModel.getPassword());

        if(!isBankStaff) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Invalid Bank Staff Credentials - %s", loginRequestModel.getUsername()));
        }

        return createAuthResponseModel(loginRequestModel.getUsername(), Role.BANK_STAFF);
    }

    private AuthResponseModel createAuthResponseModel(String username, Role role) {
        var authResponse = new AuthResponseModel();
        authResponse.setUsername(username);
        authResponse.setRole(role);

        return authResponse;
    }
}
