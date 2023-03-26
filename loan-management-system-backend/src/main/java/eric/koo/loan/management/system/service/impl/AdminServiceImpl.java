package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.service.AdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class AdminServiceImpl implements AdminService {

    @Value("${loan.management.system.admin.username}")
    private String adminUsername;

    @Value("${loan.management.system.admin.password}")
    private String adminPassword;

    private final PasswordEncoder passwordEncoder;

    AdminServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean validateUsernameAndPassword(String username, String password) {
        return username.equals(adminUsername) && passwordEncoder.matches(password, adminPassword);
    }
}
