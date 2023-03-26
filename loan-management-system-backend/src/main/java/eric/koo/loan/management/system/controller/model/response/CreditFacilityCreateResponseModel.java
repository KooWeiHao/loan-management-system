package eric.koo.loan.management.system.controller.model.response;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreditFacilityCreateResponseModel {
    private Long creditFacilityId;

    private BigDecimal creditLimit;

    private CreditFacilityEntity.Status status;
}
