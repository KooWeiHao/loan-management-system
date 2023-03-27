package eric.koo.loan.management.system.controller.model.resquest;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import eric.koo.loan.management.system.entity.LoanEntity;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class LoanCreateRequestModel {
    @NotNull
    private BigDecimal amount;

    @NotNull
    private LoanEntity.Type type;

    @NotNull
    private InterestRateEntity.Type paymentType;
}
