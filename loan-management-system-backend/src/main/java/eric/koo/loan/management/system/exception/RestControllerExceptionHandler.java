package eric.koo.loan.management.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
class RestControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoanManagementSystemException.class)
    ErrorResponseModel handleLoanManagementSystemException(LoanManagementSystemException exception, HttpServletRequest request) {
        return new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), request.getServletPath());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResponseModel handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var fieldError = exception.getBindingResult().getFieldError();
        Assert.notNull(fieldError, "Error must not be null!");
        var message = String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());

        return new ErrorResponseModel(HttpStatus.BAD_REQUEST.value(), message, request.getServletPath());
    }
}
