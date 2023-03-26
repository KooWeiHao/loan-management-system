package eric.koo.loan.management.system.config;

import eric.koo.loan.management.system.security.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorAware")
class JpaAuditingConfiguration {

    @Bean
    AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<String>{

        @Override
        public Optional<String> getCurrentAuditor() {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication instanceof AnonymousAuthenticationToken) {
                return Optional.of(Role.SYSTEM.name());
            }

            return Optional.of(authentication.getName());
        }
    }
}
