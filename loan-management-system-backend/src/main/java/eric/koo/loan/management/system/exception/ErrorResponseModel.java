package eric.koo.loan.management.system.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class ErrorResponseModel {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String path;

    ErrorResponseModel(int status, String message, String path) {
        this(status, LocalDateTime.now(), message, path);
    }
}
