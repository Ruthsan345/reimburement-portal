package com.quinbay.reimbursement.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


@Data
@Getter
@Setter
@NoArgsConstructor
public class ClaimResponse {

    private Integer claimId;

    private String employeeName;

    private String category;

    private double amount;

    private String description;

    private ArrayList<ClaimApprovalResponse> statusOfApprovers;

    public ClaimResponse(Integer claimId, String employeeName, String category, double amount, String description, ArrayList<ClaimApprovalResponse> statusOfApprovers) {
        this.claimId = claimId;
        this.employeeName = employeeName;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.statusOfApprovers = statusOfApprovers;
    }



}


