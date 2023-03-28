package eric.koo.loan.management.system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="APPLICANT")
public class ApplicantEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLICANT_ID")
    private Long applicantId;

    @NotNull
    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "APPROVED_DATE")
    private LocalDateTime approvedDate;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.PERSIST)
    private CreditFacilityEntity creditFacility;

    public enum Status {
        PROCESSING,
        APPROVED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicantEntity that = (ApplicantEntity) o;
        return Objects.equals(applicantId, that.applicantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicantId);
    }
}
