package eric.koo.loan.management.system.controller.model.response;

import eric.koo.loan.management.system.entity.CreditFacilityEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreditFacilityResponseModel {
    private Long creditFacilityId;

    private BigDecimal creditLimit;

    private CreditFacilityEntity.Status status;

    private LocalDateTime createdDate;

    private String approvedBy;

    private LocalDateTime approvedDate;
}
