package eric.koo.loan.management.system.controller.model.response;

import eric.koo.loan.management.system.entity.InterestRateEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InterestRateResponseModel {
    private BigDecimal interestRate;

    private LocalDate interestRateDate;

    private InterestRateEntity.Type type;
}
