package com.quinbay.reimbursement.model;


import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "claim")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Claim {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

//    @JoinColumn(name = "employeeid", nullable = false)
    @Column(name = "employeeid")
    public Integer employee;

    @OneToOne
//    @JoinColumn(name = "categoryid")
    public ClaimCategory claimCategory;

//    @Column(name = "image_url", columnDefinition = "text[]")
//    @Array(databaseType="varchar")
//    public List<String> image_url;

    @Column(name = "image_url")
    private String[] image_url;

    @Column(name = "from_date")
    public Date from_date;

    @Column(name = "to_date")
    public Date to_date;

    @Column(name = "description")
    public String description;

//    @ElementCollection
    @Column(name = "office_stationary_type")
    public String[] office_stationary_type;

    @Column(name = "claim_amount")
    public double claim_amount;

    @Column(name = "created_by")
    public String created_by;

    @Column(name = "created_date")
    public Date created_date;

    @Column(name = "updated_by")
    public String updated_by;

    @Column(name = "updated_date")
    public Date updated_date;


}
