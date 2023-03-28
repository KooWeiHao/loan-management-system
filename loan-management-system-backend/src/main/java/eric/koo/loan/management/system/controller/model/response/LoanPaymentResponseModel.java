package eric.koo.loan.management.system.controller.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanPaymentResponseModel {
    private Long loanId;

    private BigDecimal amount;
}
