package eric.koo.loan.management.system.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
class SecurityConfiguration {

    @Value("${loan.management.system.api.path.prefix}")
    private String apiPathPrefix;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint, AuthenticationProvider applicantAuthenticationProvider, AuthenticationProvider bankStaffAuthenticationProvider) throws Exception {
        return http.cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by Spring Security. Ensure that each and every request is re-authenticated
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authenticationProvider(bankStaffAuthenticationProvider)
                .authenticationProvider(applicantAuthenticationProvider)
                .authorizeRequests()
                .antMatchers(appendApiPathPrefix("/auth/**")).permitAll()
                .antMatchers(HttpMethod.GET, appendApiPathPrefix("/credit-limit")).permitAll()
                .antMatchers("/api/**").authenticated() // FIXME: Please use apiPathPrefix / appendApiPathPrefix()
                .anyRequest().permitAll()
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] appendApiPathPrefix(String... apis) {
        return Stream.of(apis)
                .map(api -> apiPathPrefix + api)
                .toArray(String[]::new);
    }
}
