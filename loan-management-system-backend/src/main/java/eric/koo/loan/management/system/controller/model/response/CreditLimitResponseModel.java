package eric.koo.loan.management.system.controller.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreditLimitResponseModel {
    private BigDecimal creditLimit;

    private LocalDate creditLimitDate;
}
