package eric.koo.loan.management.system.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// to handle unauthenticated requests & return an HTTP response
@Component
class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, String.format("Basic realm=\"%s\"", getRealmName()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(authException.getMessage());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName(applicationName);
        super.afterPropertiesSet();
    }
}
