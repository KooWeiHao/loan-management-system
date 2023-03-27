package eric.koo.loan.management.system.controller.model.resquest;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class CreditFacilityApproveRequestModel {
    @NotNull
    private Long creditFacilityId;

    @NotNull
    private BigDecimal creditLimit;
}
