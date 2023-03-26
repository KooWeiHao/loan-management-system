package eric.koo.loan.management.system.security.authentication.provider;

import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class UserAuthenticationProvider extends AbstractAuthenticationProvider {

    private final UserService userService;

    @Autowired
    UserAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    boolean isValid(String username, String password) {
        return userService.getByUsernameAndPassword(username, password)
                .isPresent();
    }

    @Override
    Role role() {
        return Role.USER;
    }
}
