package eric.koo.loan.management.system.controller.model.resquest;

import lombok.Getter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class CreditLimitCreateRequestModel {
    @Digits(integer = 38, fraction = 2)
    @NotNull
    private BigDecimal creditLimit;
    
    @NotNull
    private LocalDate creditLimitDate;
}
