package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Employee> findEmployeeByEmailAndIsdelete(String emailId,Boolean isDelete);

    Optional<Employee> findByIdAndIsdelete(int empid, boolean isDelete);

    ArrayList<Employee> findByManagerid(int employeeid);

    Employee findById(int Id);

    ArrayList<Employee> findByRoleName(String role);

}
