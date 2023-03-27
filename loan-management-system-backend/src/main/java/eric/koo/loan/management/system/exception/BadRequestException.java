package eric.koo.loan.management.system.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String error) {
        super(error);
    }
}
