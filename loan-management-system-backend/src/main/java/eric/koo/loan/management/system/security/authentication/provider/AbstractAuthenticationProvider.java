package eric.koo.loan.management.system.security.authentication.provider;

import eric.koo.loan.management.system.security.Role;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    abstract boolean isValid(String username, String password);
    abstract Role role();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();

        if(isValid(username, password)) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    Collections.singletonList(new SimpleGrantedAuthority(role().toString()))
            );
        }

        throw new BadCredentialsException(String.format("Invalid Credentials - %s", username));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
