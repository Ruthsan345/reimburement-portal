package com.quinbay.reimbursement.api;

import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface Claims {
    String addClaimCategory(ClaimCategory claimCategory);

    ArrayList<ClaimCategoryResponse> getAllCategories() throws UserDefinedException;

    String addClaim(ClaimRequest claimRequest) throws UserDefinedException;

    ArrayList<Claim> getAllClaims()throws UserDefinedException;

//    String addClaimUsingImage(ClaimRequest claimRequest, List<MultipartFile> files) throws IOException;

    ClaimResponseForMultipleUser getClaimsByEmployeeId(int employeeid, String status) throws UserDefinedException;

    String updateClaimStatus(ClaimUpdateRequest claimUpdateRequest) throws UserDefinedException;

    ClaimDetailResponse getClaimDetailsByClaimId(int claimid) throws UserDefinedException;

    String claimCommentRequest(ClaimCommentRequest claimCommentRequest) throws UserDefinedException;

    String addClaimUsingImage(MultipartFile[] files, int claimId) throws UserDefinedException;

    String scheduleMail() throws UserDefinedException;

    String deleteClaimUsingId(int claimId) throws UserDefinedException;
}
