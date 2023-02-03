package com.quinbay.reimbursement.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialRequest {
    private String email;
    private String password;
}
