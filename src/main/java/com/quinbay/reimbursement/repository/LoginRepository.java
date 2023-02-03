package com.quinbay.reimbursement.repository;

import com.quinbay.reimbursement.model.Employee;
import com.quinbay.reimbursement.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, String> {
    boolean existsByEmail(String email);
    Optional<Login> findByEmail(String email);
    Optional<Login> findByEmailAndIsdelete(String email,Boolean isDelete);
    Optional<Login> findByEmailAndPassword(String email, String password);
}
