package eric.koo.loan.management.system.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
class RestControllerExceptionHandler {
    
    @ExceptionHandler(BadRequestException.class)
    ErrorResponseModel handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        return new ErrorResponseModel(exception.getMessage(), request.getServletPath());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResponseModel handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var fieldError = exception.getBindingResult().getFieldError();
        Assert.notNull(fieldError, "Error must not be null!");
        var message = String.format("Required parameter '%s' is not present", fieldError.getField());

        return new ErrorResponseModel(message, request.getServletPath());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ErrorResponseModel handleMissingServletRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        var message = String.format("Required parameter '%s' is not present", exception.getParameterName());

        return new ErrorResponseModel(message, request.getServletPath());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    ErrorResponseModel handleHttpMessageNotReadableException(NestedRuntimeException exception, HttpServletRequest request) {
        return new ErrorResponseModel(exception.getMostSpecificCause().getMessage(), request.getServletPath());
    }
}
