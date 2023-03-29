package eric.koo.loan.management.system.controller.model.resquest;

import lombok.Getter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class LoanPaymentCreateRequestModel {
    @NotNull
    private Long loanId;

    @Digits(integer = 38, fraction = 2)
    @NotNull
    private BigDecimal amount;
}
