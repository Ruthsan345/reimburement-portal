package com.quinbay.reimbursement.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Data
@Getter
@Setter
public class ClaimDetailResponse {

    private Integer claimId;

    private String employeeName;

    private String category;

    private String[] imageUrl;

    private Date fromDate;

    private Date toDate;

    private String[] officeStationaryType;

    private String description;

    private double amount;

    private java.util.Date claimCreateDate;

    private ArrayList<ClaimCommentsResponse> claimCommentsResponses;

    private ArrayList<ClaimApprovalResponse> statusOfApprovers;

    public ClaimDetailResponse(Integer claimId, String employeeName, String category, String[] imageUrl, Date fromDate, Date toDate, String[] officeStationaryType, String description, double amount, java.util.Date claimCreateDate) {
        this.claimId = claimId;
        this.employeeName = employeeName;
        this.category = category;
        this.imageUrl = imageUrl;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.officeStationaryType = officeStationaryType;
        this.description = description;
        this.amount = amount;
        this.claimCreateDate = claimCreateDate;
    }

}
