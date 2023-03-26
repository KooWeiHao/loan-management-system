package eric.koo.loan.management.system.security;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

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
