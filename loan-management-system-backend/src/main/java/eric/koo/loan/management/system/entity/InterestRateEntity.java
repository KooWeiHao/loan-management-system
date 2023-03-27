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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="INTEREST_RATE")
public class InterestRateEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTEREST_RATE_ID")
    private Long interestRateId;

    @NotNull
    @Column(name = "INTEREST_RATE")
    private BigDecimal interestRate;

    @NotNull
    @Column(name = "INTEREST_RATE_DATE")
    private LocalDate interestRateDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private Type type;

    public enum Type {
        FULL_PAYMENT,
        PARTIAL_PAYMENT_ONE_YEAR,
        PARTIAL_PAYMENT_TWO_YEARS
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestRateEntity that = (InterestRateEntity) o;
        return Objects.equals(interestRateId, that.interestRateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interestRateId);
    }
}
