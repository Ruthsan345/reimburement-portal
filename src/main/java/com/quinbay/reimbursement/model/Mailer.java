package com.quinbay.reimbursement.model;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mailer {
    private String mailId;
    private String message;
}
