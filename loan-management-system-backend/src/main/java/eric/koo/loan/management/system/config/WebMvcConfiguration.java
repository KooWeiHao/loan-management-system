package eric.koo.loan.management.system.config;

import eric.koo.loan.management.system.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Value("${loan.management.system.api.path.prefix}")
    private String apiPathPrefix;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        logger.info("Base path of API - {}", apiPathPrefix);

        // To specify the base path of APIs and base package of APIs
        configurer.addPathPrefix(
                apiPathPrefix,
                HandlerTypePredicate.forBasePackageClass(Controller.class)
        );
    }
}
