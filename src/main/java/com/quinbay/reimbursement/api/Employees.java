package com.quinbay.reimbursement.api;

import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.CredentialRequest;
import com.quinbay.reimbursement.model.Employee;
import com.quinbay.reimbursement.model.EmployeeDetailResponse;

public interface Employees {
    String addEmployee(Employee employee) throws UserDefinedException;

    String setCredentials(CredentialRequest login);

    EmployeeDetailResponse getEmployeeDetailsByEmail(String emailId) throws UserDefinedException;

    String authLogin(CredentialRequest loginPojo) throws UserDefinedException;
}
