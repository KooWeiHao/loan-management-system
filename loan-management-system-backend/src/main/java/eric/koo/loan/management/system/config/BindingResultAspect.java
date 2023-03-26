package eric.koo.loan.management.system.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Aspect
@Component
class BindingResultAspect {

    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    void handleBindingResult(JoinPoint joinPoint) {
        var bindingResult = Arrays.stream(joinPoint.getArgs())
                .filter(args -> args instanceof BindingResult)
                .map(args -> (BindingResult) args)
                .findFirst();

        bindingResult.ifPresent(result -> {
            if(result.hasErrors()) {
                final String errorMessage;
                var error = result.getAllErrors().get(0);

                if(error instanceof FieldError) {
                    var fieldError = (FieldError) error;
                    errorMessage = String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());
                } else {
                    errorMessage = error.getDefaultMessage();
                }

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
            }
        });
    }
}
