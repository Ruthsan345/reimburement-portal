package com.quinbay.reimbursement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "claim_comments")
@Data
@Getter
@Setter

public class ClaimComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name ="claimid")
    public Integer claimid;

    @Column(name = "comments")
    public String comments;

    @Column(name="employeeid")
    public Integer employeeid;

    @Column(name = "date")
    public Date date;

    @Column(name = "updated_by")
    public String updated_by;

    @Column(name = "updated_date")
    public Date updated_date;


}




