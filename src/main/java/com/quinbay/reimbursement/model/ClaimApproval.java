package com.quinbay.reimbursement.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "claim_approval")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimApproval implements Serializable {

    @Id
    @SequenceGenerator(name = "claim_approval_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "claim_approval_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Claim claim;

    private Integer approverid;

    private int level;

    public String status;

    private double approved_Amount;

    private String created_by;

    @CreationTimestamp
    private Date created_date;

    private String updated_by;

    @UpdateTimestamp
    private Date updated_date;

    private boolean isdelete= false;

    public ClaimApproval(Claim claim, Integer approverid, int level, String status) {
        this.claim = claim;
        this.approverid = approverid;
        this.level = level;
        this.status = status;
    }


}
