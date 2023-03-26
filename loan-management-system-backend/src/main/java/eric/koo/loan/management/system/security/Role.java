package eric.koo.loan.management.system.security;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    APPLICANT("ROLE_APPLICANT"),
    BANK_STAFF("ROLE_BANK_STAFF"),
    SYSTEM("ROLE_SYSTEM");

    private final String role;

    Role(String role){
        this.role = role;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.role;
    }
}
