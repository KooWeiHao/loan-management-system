package eric.koo.loan.management.system.security.authentication.provider;

import eric.koo.loan.management.system.security.Role;
import eric.koo.loan.management.system.service.BankStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class BankStaffAuthenticationProvider extends AbstractAuthenticationProvider {

    private final BankStaffService bankStaffService;

    @Autowired
    BankStaffAuthenticationProvider(BankStaffService bankStaffService) {
        this.bankStaffService = bankStaffService;
    }

    @Override
    boolean isValid(String username, String password) {
        return bankStaffService.validateUsernameAndPassword(username, password);
    }

    @Override
    Role role() {
        return Role.BANK_STAFF;
    }
}
