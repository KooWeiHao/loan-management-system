package eric.koo.loan.management.system.controller.model.response;

import eric.koo.loan.management.system.entity.LoanEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponseModel {
    private String applicantUsername;

    private Long loanId;

    private LoanEntity.Type type;

    private BigDecimal principalAmount;

    private BigDecimal interestRate;

    private LoanEntity.Status status;

    private LocalDateTime firstPaymentDate;

    private LocalDateTime lastPaymentDate;

    private LocalDateTime createdDate;

    private String approvedBy;

    private LocalDateTime approvedDate;
}
