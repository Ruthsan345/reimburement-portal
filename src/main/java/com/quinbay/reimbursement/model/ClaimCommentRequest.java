package com.quinbay.reimbursement.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ClaimCommentRequest {

    private String comments;
    private Integer employeeId;
    private Integer claimId;


}