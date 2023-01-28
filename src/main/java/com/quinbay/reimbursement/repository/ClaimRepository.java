package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {
    ArrayList<Claim> findByEmployee(int employeeid);
}
