package com.quinbay.reimbursement.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;


@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponse {

    Integer claimId;

    public String employeeName;

    public String category;

    public double amount;

    public String description;

    public ArrayList<ClaimApprovalResponse> statusOfApprovers;

}


