package eric.koo.loan.management.system.controller.model.response;

import eric.koo.loan.management.system.entity.ApplicantEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicantResponseModel {
    private Long applicantId;

    private String username;

    private ApplicantEntity.Status status;

    private LocalDateTime createdDate;

    private String approvedBy;

    private LocalDateTime approvedDate;

    private CreditFacilityModel creditFacility;

    @Getter
    @Setter
    public static class CreditFacilityModel {
        private Long creditFacilityId;

        private BigDecimal creditLimit;
    }
}
