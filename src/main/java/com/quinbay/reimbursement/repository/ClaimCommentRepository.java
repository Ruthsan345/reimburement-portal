package com.quinbay.reimbursement.repository;


import com.quinbay.reimbursement.model.ClaimComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimCommentRepository extends JpaRepository<ClaimComments, Integer> {
}
