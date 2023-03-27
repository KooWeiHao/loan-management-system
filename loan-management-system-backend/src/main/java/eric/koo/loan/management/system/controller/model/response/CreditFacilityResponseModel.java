package eric.koo.loan.management.system.controller.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreditFacilityResponseModel {
    private Long creditFacilityId;

    private String applicantUsername;

    private BigDecimal creditLimit;

    private LocalDateTime createdDate;

    private String approvedBy;

    private LocalDateTime approvedDate;
}
