package com.quinbay.reimbursement.repository;


import com.quinbay.reimbursement.model.ClaimComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ClaimCommentRepository extends JpaRepository<ClaimComment, Integer> {

    @Query(value ="select * from claim_comments c where c.claim_id = :claimId order by c.date asc", nativeQuery = true)
    ArrayList<ClaimComment> findByClaimidDateDesc(@Param("claimId") int claimId);
}
