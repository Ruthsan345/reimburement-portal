package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.ClaimCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimCategoryRepository extends JpaRepository<ClaimCategory, Integer> {
    ClaimCategory findById(int claimCategoryId);
}
