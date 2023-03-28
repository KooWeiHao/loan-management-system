package eric.koo.loan.management.system.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class ErrorResponseModel {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String path;

    ErrorResponseModel(String message, String path) {
        this(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), message, path);
    }
}
