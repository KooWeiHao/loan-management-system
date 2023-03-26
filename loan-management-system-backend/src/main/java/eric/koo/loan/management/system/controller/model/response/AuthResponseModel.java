package eric.koo.loan.management.system.controller.model.response;

import eric.koo.loan.management.system.security.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseModel {
    private String username;

    private Role role;
}
