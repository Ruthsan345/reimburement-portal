package com.quinbay.reimbursement.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "employee")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @SequenceGenerator(name = "employee_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employee_seq")
    private Integer id;

    private String name;

    private Double phone;

    @OneToOne
    private Login auth;

    private String job_title;

    @OneToOne
    public Role role;

    private Integer managerid;

    private String created_by;

    @CreationTimestamp
    private Date created_date;

    private String updated_by;

    @UpdateTimestamp
    private Date updated_date;

    private boolean isdelete=false;

    public Employee(String name, Double phone, Login login, String job_title, Role role, Integer managerid) {
        this.name = name;
        this.phone = phone;
        this.auth = login;
        this.job_title = job_title;
        this.role = role;
        this.managerid = managerid;
    }
}
