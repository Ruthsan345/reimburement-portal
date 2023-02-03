package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.Claim;
import com.quinbay.reimbursement.model.Employee;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {
    ArrayList<Claim> findByEmployeeIdAndIsdelete(int employeeid,Boolean isDelete);
    ArrayList<Claim> findByIsdeleteAndEmployeeId(Boolean isDelete,int employeeid);
    Optional<Claim> findByIdAndIsdelete(int claimId, Boolean isDelete);

    @Query(value ="select email from employee e where e.id = (select c.employee_id from claim c where c.id= :claimId) ", nativeQuery = true)
    String findEmployeMailByClaimId(@Param("claimId") int claimId);

    ArrayList<Claim>  findByIsdelete(boolean b);

    ArrayList<Claim> findByIsdeleteAndEmployeeManagerid(Boolean isDelete, int employeeid);
}
