package eric.koo.loan.management.system.security;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured("ROLE_APPLICANT")
@ApiOperation(value = "", notes = "This endpoint only available for APPLICANT")
public @interface RoleApplicant {
    @AliasFor(annotation = ApiOperation.class)
    String value();
}
