package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.ClaimApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ClaimApprovalRepository extends JpaRepository<ClaimApproval, Integer> {
    ArrayList<ClaimApproval> findByClaimid(int claimId);
    ArrayList<ClaimApproval> findByApproverid(int claimId);

}
