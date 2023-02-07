package com.quinbay.reimbursement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "claim_comments")
@Data
@Getter
@Setter
public class ClaimComment implements Serializable {
    @Id
    @SequenceGenerator(name = "claim_comment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "claim_comment_seq")
    private Integer id;

    @ManyToOne( fetch = FetchType.EAGER)
    @JsonBackReference
    private Claim claim;

    private String comments;

    private Integer employeeid;

    @CreationTimestamp
    private Date date;

    private String updated_by;

    @UpdateTimestamp
    private Date updated_date;

}




