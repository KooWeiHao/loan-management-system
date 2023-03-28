package eric.koo.loan.management.system.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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

    @Value("${loan.management.system.public.api}")
    private String[] publicApi;

    @Value("${loan.management.system.application.public.api}")
    private String[] applicationPublicApi;

    @Value("${loan.management.system.applicant.post.api}")
    private String[] applicantPostApi;

    @Value("${loan.management.system.bank.staff.post.api}")
    private String[] bankStaffPostApi;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint, AuthenticationProvider applicantAuthenticationProvider, AuthenticationProvider bankStaffAuthenticationProvider) throws Exception {
        return http.cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authenticationProvider(bankStaffAuthenticationProvider)
                .authenticationProvider(applicantAuthenticationProvider)
                .authorizeRequests()
                .antMatchers(publicApi).permitAll()
                .antMatchers(appendApiPathPrefix(applicationPublicApi)).permitAll()
                .antMatchers(HttpMethod.POST, appendApiPathPrefix(applicantPostApi)).hasRole(Role.APPLICANT.name())
                .antMatchers(HttpMethod.POST, appendApiPathPrefix(bankStaffPostApi)).hasRole(Role.BANK_STAFF.name())
                .anyRequest().authenticated()
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] appendApiPathPrefix(String[] apis) {
        return Stream.of(apis)
                .map(api -> apiPathPrefix + api)
                .toArray(String[]::new);
    }
}
