package com.quinbay.reimbursement.service.impl;

import com.quinbay.reimbursement.api.Employees;
import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.CredentialRequest;
import com.quinbay.reimbursement.model.Employee;
import com.quinbay.reimbursement.model.EmployeeDetailResponse;
import com.quinbay.reimbursement.model.Login;
import com.quinbay.reimbursement.repository.EmployeeRepository;
import com.quinbay.reimbursement.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class EmployeeOperation implements Employees {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LoginRepository loginRepository;

    java.sql.Date date;
    long millis=System.currentTimeMillis();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public String addEmployee(Employee employee) throws UserDefinedException {
            if(employeeRepository.existsByEmail(employee.getEmail())){throw new UserDefinedException("Email Already Exists");}
            Employee createdAccount = employeeRepository.save(employee);
            return "Account creation success! Your employee ID : "+createdAccount.getId();
    }

    @Override
    public String setCredentials(CredentialRequest loginPojo) {
        date=new java.sql.Date(millis);

        String encodedPassword = passwordEncoder.encode(loginPojo.getPassword());
        Login login = new Login();
        login.setEmail(loginPojo.getEmail());
        login.setRegistrationmethod("GENERAL");
        login.setNoofAtemptsPasswordFailureCount(0);
        login.setPassword(encodedPassword);
        loginRepository.save(login);
        return "Password set for :"+login.getEmail();
    }

    @Override
    public EmployeeDetailResponse getEmployeeDetailsByEmail(String emailId) throws UserDefinedException {

        Employee employee = employeeRepository.findEmployeeByEmail(emailId).orElseThrow(() -> new UserDefinedException("Employee details not found"));
        EmployeeDetailResponse employeeDetailResponse = new EmployeeDetailResponse() ;
        employeeDetailResponse.setEmail(employee.getEmail());
        employeeDetailResponse.setId(employee.getId());
        employeeDetailResponse.setJob_title(employee.getJob_title());
        employeeDetailResponse.setManagerid(employee.getManagerid());
        employeeDetailResponse.setName(employee.getName());
        employeeDetailResponse.setPhone(employee.getPhone());
        employeeDetailResponse.setRole(employee.getRole());

        return employeeDetailResponse;

    }

    @Override
    public String authLogin(CredentialRequest loginPojo) throws UserDefinedException {
       Login login= loginRepository.findByEmail(loginPojo.getEmail()).orElseThrow(()-> new UserDefinedException("Invalid Email id"));
       System.out.print(login.getPassword()+""+loginPojo.getPassword());
       System.out.print(passwordEncoder.matches(loginPojo.getPassword(),login.getPassword()));
        if (passwordEncoder.matches(loginPojo.getPassword(), login.getPassword())) {
            return "Login successfull";
        } else {
            throw new UserDefinedException("Invalid Password");
        }
    }
}
