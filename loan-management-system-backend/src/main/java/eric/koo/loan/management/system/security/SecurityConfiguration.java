package eric.koo.loan.management.system.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

@EnableWebSecurity
class SecurityConfiguration {

    @Value("${loan.management.system.api.path.prefix}")
    private String apiPathPrefix;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint, AuthenticationProvider applicantAuthenticationProvider, AuthenticationProvider bankStaffAuthenticationProvider) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authenticationProvider(bankStaffAuthenticationProvider)
                .authenticationProvider(applicantAuthenticationProvider)
                .authorizeRequests()
                .antMatchers("/error").permitAll()
                .antMatchers(appendApiPathPrefix("/auth/**")).permitAll()
                .antMatchers(appendApiPathPrefix("/credit-facility/approve")).hasRole(Role.BANK_STAFF.name())
                .anyRequest().hasRole(Role.APPLICANT.name())
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
