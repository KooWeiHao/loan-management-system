package eric.koo.loan.management.system.security.authentication.provider;

import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class AdminAuthenticationProvider extends AbstractAuthenticationProvider {

    private final AdminService adminService;

    @Autowired
    AdminAuthenticationProvider(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    boolean isValid(String username, String password) {
        return adminService.validateUsernameAndPassword(username, password);
    }

    @Override
    Role role() {
        return Role.ADMIN;
    }
}
