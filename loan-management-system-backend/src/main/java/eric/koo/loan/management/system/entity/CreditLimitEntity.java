package eric.koo.loan.management.system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="CREDIT_LIMIT")
public class CreditLimitEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CREDIT_LIMIT_ID")
    private Long creditLimitId;

    @NotNull
    @Column(name = "CREDIT_LIMIT")
    private BigDecimal creditLimit;

    @NotNull
    @Column(name = "CREDIT_LIMIT_DATE")
    private LocalDate creditLimitDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditLimitEntity that = (CreditLimitEntity) o;
        return Objects.equals(creditLimitId, that.creditLimitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditLimitId);
    }
}
