package com.quinbay.reimbursement.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "claim_category")
@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class ClaimCategory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "created_by")
    public String created_by;

    @Column(name = "created_date")
    public Date created_date;

    @Column(name = "updated_by")
    public String updated_by;

    @Column(name = "updated_date")
    public Date updated_date;


}
