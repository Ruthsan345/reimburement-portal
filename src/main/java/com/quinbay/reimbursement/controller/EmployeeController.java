package com.quinbay.reimbursement.controller;


import com.quinbay.reimbursement.api.Employees;
import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.CredentialRequest;
import com.quinbay.reimbursement.model.Employee;
import com.quinbay.reimbursement.model.EmployeeDetailResponse;
import com.quinbay.reimbursement.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://769b-103-224-35-103.ap.ngrok.io", maxAge = 3600)
@RequestMapping("employee/api/")
@RestController()
public class EmployeeController {

    @Autowired
    Employees employeeOp;


    @PostMapping("/addEmployee")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {

        try {
            String result = employeeOp.addEmployee(employee);
            return ResponseHandler.generateResponse("Successfully Added Employee", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/setCredentials")
    public ResponseEntity<Object> setCredentials(@RequestBody CredentialRequest login) {

        try {
            String result = employeeOp.setCredentials(login);
            return ResponseHandler.generateResponse("Successfully added the password!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @GetMapping("/getEmployeeDetailsByEmail")
    public ResponseEntity<Object> getEmployeeDetailsByEmail(@RequestParam String emailId) {

        try {
            EmployeeDetailResponse result = employeeOp.getEmployeeDetailsByEmail(emailId);
            return ResponseHandler.generateResponse("Success got employee details", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody CredentialRequest loginPojo) {
        try {
            String result = employeeOp.authLogin(loginPojo);
            return ResponseHandler.generateResponse("Login Success", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }







}
