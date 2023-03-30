package eric.koo.loan.management.system.config;

import eric.koo.loan.management.system.controller.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.InputStream;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Configuration
class SwaggerConfiguration {
    /*
        Page - /swagger-ui/
        Docs - /v2/api-docs?{groupName}
    */

    @Value("${loan.management.system.api.path.prefix}")
    private String apiPathPrefix;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Loan Management System")
                .apiInfo(apiInfo())
                .ignoredParameterTypes(Principal.class, Authentication.class, InputStream.class, Resource.class)
                .securityContexts(Collections.singletonList(actuatorSecurityContext()))
                .securitySchemes(Collections.singletonList(basicAuthScheme()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(Controller.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Loan Management System APIs")
                .description("APIs specification")
                .version("v1.0")
                .license("v1.0")
                .contact(new Contact("Eric Koo Wei Hao", "https://github.com/KooWeiHao", "ericcool.1129@gmail.com"))
                .build();
    }

    private SecurityContext actuatorSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(basicAuthReference()))
                .forPaths(PathSelectors.ant(String.format("%s/**", apiPathPrefix)))
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }
}
