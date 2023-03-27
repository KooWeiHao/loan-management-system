package eric.koo.loan.management.system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="LOAN")
public class LoanEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_ID")
    private Long loanId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CREDIT_FACILITY_ID")
    private CreditFacilityEntity creditFacility;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private Type type;

    @NotNull
    @Column(name = "PRINCIPAL")
    private BigDecimal principal;

    @NotNull
    @Column(name = "INTEREST_RATE")
    private BigDecimal interestRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @NotNull
    @Column(name = "FIRST_PAYMENT_DATE")
    private LocalDateTime firstPaymentDate;

    @NotNull
    @Column(name = "LAST_PAYMENT_DATE")
    private LocalDateTime lastPaymentDate;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "APPROVED_DATE")
    private LocalDateTime approvedDate;

    public enum Type {
        HOME,
        CAR
    }

    enum Status {
        PROCESSING,
        ACTIVE,
        PAID
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanEntity that = (LoanEntity) o;
        return Objects.equals(loanId, that.loanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId);
    }
}
