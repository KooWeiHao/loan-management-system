package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.service.BankStaffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class BankStaffServiceImpl implements BankStaffService {

    @Value("${loan.management.system.bank.staff.username}")
    private String bankStaffUsername;

    @Value("${loan.management.system.bank.staff.password}")
    private String bankStaffPassword;

    private final PasswordEncoder passwordEncoder;

    BankStaffServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean validateUsernameAndPassword(String username, String password) {
        return username.equals(bankStaffUsername) && passwordEncoder.matches(password, bankStaffPassword);
    }
}
