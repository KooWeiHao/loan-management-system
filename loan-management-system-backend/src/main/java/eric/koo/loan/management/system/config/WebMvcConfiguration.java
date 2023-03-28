package eric.koo.loan.management.system.config;

import eric.koo.loan.management.system.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Value("${loan.management.system.api.path.prefix}")
    private String apiPathPrefix;

    @Value("${loan.management.system.allowed.origins}")
    private String[] allowedOrigins;

    @Value("${loan.management.system.allowed.methods}")
    private String[] allowedMethods;

    @Value("${loan.management.system.allowed.headers}")
    private String[] allowedHeaders;

    @Value("${loan.management.system.max.age}")
    private Long maxAge;

    @Value("${loan.management.system.allow.credentials}")
    private boolean allowCredentials;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        logger.info("Base path of API - {}", apiPathPrefix);

        // To specify the base path of APIs and base package of APIs
        configurer.addPathPrefix(
                apiPathPrefix,
                HandlerTypePredicate.forBasePackageClass(Controller.class)
        );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("Allowed Origins - {}", Arrays.toString(allowedOrigins));
        logger.info("Allowed Methods - {}", Arrays.toString(allowedMethods));
        logger.info("Allowed Headers - {}", Arrays.toString(allowedHeaders));
        logger.info("Max Age - {}", maxAge);
        logger.info("Allow Credentials - {}", allowCredentials);

        registry.addMapping("/**")
                .allowedOriginPatterns(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders)
                .maxAge(maxAge)
                .allowCredentials(allowCredentials);
    }
}
