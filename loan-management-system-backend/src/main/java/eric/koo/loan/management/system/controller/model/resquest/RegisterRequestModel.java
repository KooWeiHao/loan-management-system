package eric.koo.loan.management.system.controller.model.resquest;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RegisterRequestModel {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
