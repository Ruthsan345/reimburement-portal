package com.quinbay.reimbursement.api;

import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Claims {
    String addClaimCategory(ClaimCategory claimCategory);

    ArrayList<ClaimCategory> getAllCategories() throws UserDefinedException;

    String addClaim(ClaimRequest claimRequest);

    ArrayList<Claim> getAllClaims()throws UserDefinedException;

    String addClaimUsingImage(ClaimRequest claimRequest, List<MultipartFile> files) throws IOException;

    ArrayList<ClaimResponse> getClaimsByEmployeeId(int employeeid, String status, String role) throws UserDefinedException;

    String updateClaimStatus(ClaimUpdateRequest claimUpdateRequest);
}
