package com.quinbay.reimbursement.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "auth")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @Id
    @Column(name = "email",  unique = true)
    public String email;

    @Column(name = "password")
    public String password;

    @Column(name = "Registrationmethod")
    public String Registrationmethod;

    @Column(name = "Lastsuccessfullogin")
    public Date Lastsuccessfullogin;

    @Column(name = "Lastfailedlogin")
    public Date Lastfailedlogin;

    @Column(name = "noofAtemptsPasswordFailureCount")
    public int noofAtemptsPasswordFailureCount;

}
