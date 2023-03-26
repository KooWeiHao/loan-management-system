package eric.koo.loan.management.system.controller;

import eric.koo.loan.management.system.controller.model.response.AuthResponseModel;
import eric.koo.loan.management.system.controller.model.resquest.LoginRequestModel;
import eric.koo.loan.management.system.controller.model.resquest.RegisterRequestModel;
import eric.koo.loan.management.system.entity.UserEntity;
import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.AdminService;
import eric.koo.loan.management.system.service.UserService;
import org.springframework.beans.BeanUtils;
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

    private final UserService userService;
    private final AdminService adminService;

    private final Validator registerRequestValidator;

    @Autowired
    AuthController(UserService userService, AdminService adminService, @Qualifier("registerRequestValidator") Validator registerRequestValidator) {
        this.userService = userService;
        this.adminService = adminService;
        this.registerRequestValidator = registerRequestValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registerRequestValidator);
    }

    @PostMapping("/register")
    AuthResponseModel register(@Valid @RequestBody RegisterRequestModel registerRequestModel, BindingResult bindingResult) {
        var newUser = userService.createUser(registerRequestModel.getUsername(), registerRequestModel.getPassword());

        return getAuthResponseModelByUserEntity(newUser, Role.USER);
    }

    @PostMapping("/login")
    AuthResponseModel login(@Valid @RequestBody LoginRequestModel loginRequestModel, BindingResult bindingResult) {
        var user = userService.getByUsernameAndPassword(loginRequestModel.getUsername(), loginRequestModel.getPassword());

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Invalid Credentials - %s", loginRequestModel.getUsername()));
        }

        return getAuthResponseModelByUserEntity(user.get(), Role.USER);
    }

    @PostMapping("/login-as-admin")
    AuthResponseModel loginAsAdmin(@Valid @RequestBody LoginRequestModel loginRequestModel, BindingResult bindingResult) {
        var isAdmin = adminService.validateUsernameAndPassword(loginRequestModel.getUsername(), loginRequestModel.getPassword());

        if(!isAdmin) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Invalid Admin Credentials - %s", loginRequestModel.getUsername()));
        }

        var admin = new UserEntity();
        BeanUtils.copyProperties(loginRequestModel, admin);

        return getAuthResponseModelByUserEntity(admin, Role.ADMIN);
    }

    private AuthResponseModel getAuthResponseModelByUserEntity(UserEntity userEntity, Role role) {
        var authResponse = new AuthResponseModel();
        BeanUtils.copyProperties(userEntity, authResponse);
        authResponse.setRole(role);

        return authResponse;
    }
}
