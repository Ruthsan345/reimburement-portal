package com.quinbay.reimbursement.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import lombok.*;



@Entity
@Table(name = "employee")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

//    public Employee(int id, String name, Double phone, String email, String job_title, String role, int managerid, String created_by, Date created_date, String updated_by, Date updated_date) {
//        this.id = id;
//        this.name = name;
//        this.phone = phone;
//        this.email = email;
//        this.job_title = job_title;
//        this.role = role;
//        this.managerid = managerid;
//        this.created_by = created_by;
//        this.created_date = created_date;
//        this.updated_by = updated_by;
//        this.updated_date = updated_date;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "phone")
    public Double phone;

    @Column(name = "email",unique = true)
    public String email;

    @Column(name = "job_title")
    public String job_title;

    @Column(name = "role")
    public String role;

    @Column(name = "managerid", nullable=true)
    public int managerid;

    @Column(name = "created_by")
    public String created_by;

    @Column(name = "created_date")
    public Date created_date;

    @Column(name = "updated_by")
    public String updated_by;

    @Column(name = "updated_date")
    public Date updated_date;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public List<Claim> claims;

}
