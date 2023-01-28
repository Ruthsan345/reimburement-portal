package com.quinbay.reimbursement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Data
@Getter
@Setter
public class CredentialRequest {
    public String email;
    public String password;
}
