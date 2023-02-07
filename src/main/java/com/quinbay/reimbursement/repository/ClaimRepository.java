package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {
    ArrayList<Claim> findByIsdeleteAndEmployeeId(Boolean isDelete,int employeeid, Pageable pageable);
    Optional<Claim> findByIdAndIsdelete(int claimId, Boolean isDelete);
    ArrayList<Claim>  findByIsdelete(boolean b);
}
