package com.quinbay.reimbursement.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "auth")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @Id
    @Column(unique = true)
    private String email;

    private String password;

    private String Registrationmethod;

    private Date Lastsuccessfullogin;

    private Date Lastfailedlogin;

    private int noofAtemptsPasswordFailureCount;

    private boolean isdelete =false;
}
