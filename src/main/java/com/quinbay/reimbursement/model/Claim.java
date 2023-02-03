package com.quinbay.reimbursement.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "claim")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Claim implements Serializable {


    @Id
    @SequenceGenerator(name = "claim_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "claim_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employee;

    @OneToOne
    public ClaimCategory claimCategory;

    private String[] image_url;

    private Date from_date;

    private Date to_date;

    private String description;

    private String[] office_stationary_type;

    private double claim_amount;

    private String created_by;

    @CreationTimestamp
    private Date created_date;

    private String updated_by;

    @UpdateTimestamp
    private Date updated_date;

    private boolean isdelete=false;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<ClaimApproval> claimApprovals;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<ClaimComment> ClaimComment;

}
