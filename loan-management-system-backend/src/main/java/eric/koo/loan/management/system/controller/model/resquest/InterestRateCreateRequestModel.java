package eric.koo.loan.management.system.controller.model.resquest;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class InterestRateCreateRequestModel {
    @NotNull
    private BigDecimal interestRateInPercentage;

    @NotNull
    private LocalDate interestRateDate;

    @NotNull
    private InterestRateEntity.Type type;
}
