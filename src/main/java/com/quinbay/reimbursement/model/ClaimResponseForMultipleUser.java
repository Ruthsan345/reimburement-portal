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
public class ClaimResponseForMultipleUser {

    private ArrayList<ClaimResponse> myClaims;

    private ArrayList<ClaimResponse> employeeClaims;
//
//    private int currentPage;
//
//    private int totalItems;
//
//    private int totalPages;

}
