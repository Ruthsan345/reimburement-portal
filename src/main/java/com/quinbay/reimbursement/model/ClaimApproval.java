package com.quinbay.reimbursement.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "claim_approval")
@Data
@Getter
@Setter
public class ClaimApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name ="claimid")
    public Integer claimid;

    @Column(name = "approverid")
    public Integer approverid;

    @Column(name = "level")
    public int level;

    @Column(name = "status")
    public String status;

    @Column(name = "approved_Amount")
    public double approved_Amount;

    @Column(name = "created_by")
    public String created_by;

    @Column(name = "created_date")
    public Date created_date;

    @Column(name = "updated_by")
    public String updated_by;

    @Column(name = "updated_date")
    public Date updated_date;
    
}
