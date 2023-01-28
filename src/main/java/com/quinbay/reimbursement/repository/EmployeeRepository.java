package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Employee> findEmployeeByEmail(String emailId);

    Employee findById(int empid);

    ArrayList<Employee> findByManagerid(int employeeid);

//    Wholesaler findById(int Id);

}
