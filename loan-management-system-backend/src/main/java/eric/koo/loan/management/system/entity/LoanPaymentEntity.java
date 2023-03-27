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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="LOAN_PAYMENT")
public class LoanPaymentEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_PAYMENT_ID")
    private Long loanPaymentId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "LOAN_ID")
    private LoanEntity loan;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPaymentEntity that = (LoanPaymentEntity) o;
        return Objects.equals(loanPaymentId, that.loanPaymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanPaymentId);
    }
}
