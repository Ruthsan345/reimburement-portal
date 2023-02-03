package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.ClaimCategory;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimCategoryRepository extends JpaRepository<ClaimCategory, Integer> {
    ClaimCategory findByIdAndIsdelete(int claimCategoryId, Boolean isDelete);
}
