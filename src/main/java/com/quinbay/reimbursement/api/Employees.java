package com.quinbay.reimbursement.api;

import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.*;

import java.util.ArrayList;

public interface Employees {
    String addEmployee(EmployeeDetailRequest employee) throws UserDefinedException;

    String setCredentials(CredentialRequest login);

    EmployeeDetailResponse getEmployeeDetailsByEmail(String emailId) throws UserDefinedException;

    String authLogin(CredentialRequest loginPojo) throws UserDefinedException;

    String deleteEmployeeUsingId(int empId)throws UserDefinedException;

    String assignEmployeeToManager(AssignEmployeeToManager employee) throws UserDefinedException;

    String addRole(RoleRequest employee) throws UserDefinedException;

    ArrayList<RoleResponse> getAllRoles() throws UserDefinedException;
}
