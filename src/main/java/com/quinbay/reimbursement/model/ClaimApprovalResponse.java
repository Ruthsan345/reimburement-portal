package com.quinbay.reimbursement.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClaimApprovalResponse {
    public Integer approverid;
    public int level;
    public String status;
}
