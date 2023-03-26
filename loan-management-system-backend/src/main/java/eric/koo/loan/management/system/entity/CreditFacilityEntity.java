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
import javax.persistence.OneToOne;
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
@Table(name="CREDIT_FACILITY")
public class CreditFacilityEntity extends AbstractEntity{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CREDIT_FACILITY_ID")
    private Long creditFacilityId;

    @NotNull
    @OneToOne
    @JoinColumn(name = "APPLICANT_ID")
    private ApplicantEntity applicant;

    @NotNull
    @Column(name = "CREDIT_LIMIT")
    private BigDecimal creditLimit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "APPROVED_DATE")
    private LocalDateTime approvedDate;

    enum Status {
        PENDING,
        APPROVED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditFacilityEntity that = (CreditFacilityEntity) o;
        return Objects.equals(creditFacilityId, that.creditFacilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditFacilityId);
    }
}
