package com.quinbay.reimbursement.model;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimApprovalResponse {
    private Integer approverId;
    private int level;
    private String status;
}
