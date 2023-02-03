package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.ClaimApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface ClaimApprovalRepository extends JpaRepository<ClaimApproval, Integer> {
    Optional<ArrayList<ClaimApproval>> findByClaimId(int claimId);
    ArrayList<ClaimApproval> findByApproveridAndIsdelete(int claimId, Boolean isDelete);

    ArrayList<ClaimApproval> findByStatus(String pending);

    int countByApproveridAndStatus(Integer id,String status);
}
