package com.quinbay.reimbursement.service.impl;

import com.quinbay.reimbursement.api.Employees;
import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.*;
import com.quinbay.reimbursement.repository.EmployeeRepository;
import com.quinbay.reimbursement.repository.LoginRepository;
import com.quinbay.reimbursement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;


@Service
public class EmployeeOperation implements Employees {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    RoleRepository roleRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public String addEmployee(EmployeeDetailRequest employee) throws UserDefinedException {
            if(employeeRepository.existsByAuthEmail(employee.getEmail())){throw new UserDefinedException("Email Already Exists");}
            Role role = roleRepository.findByName(employee.getRole()).orElseThrow(() -> new UserDefinedException("Role not found"));

            CredentialRequest credentialRequest = new CredentialRequest(employee.getEmail(),employee.getPassword());
            Login login = saveCredentials(credentialRequest);

            Employee newEmployee = new Employee(employee.getName(),employee.getPhone(),login,employee.getJobTitle(),role,employee.getManagerId());
            Employee createdAccount = employeeRepository.save(newEmployee);

            return "Account creation success! Your employee ID : "+createdAccount.getId();
    }

    @Override
    public String setCredentials(CredentialRequest loginPojo) {
        String encodedPassword = passwordEncoder.encode(loginPojo.getPassword());
        Login login = new Login();
        login.setEmail(loginPojo.getEmail());
        login.setRegistrationmethod("GENERAL");
        login.setNoofAtemptsPasswordFailureCount(0);
        login.setPassword(encodedPassword);
        loginRepository.save(login);
        return "Password set for :"+login.getEmail();
    }

    public Login saveCredentials(CredentialRequest loginPojo) {
        String encodedPassword = passwordEncoder.encode(loginPojo.getPassword());
        Login login = new Login();
        login.setEmail(loginPojo.getEmail());
        login.setRegistrationmethod("GENERAL");
        login.setNoofAtemptsPasswordFailureCount(0);
        login.setPassword(encodedPassword);
        login = loginRepository.save(login);
        return login;
    }

    @Override
    public EmployeeDetailResponse getEmployeeDetailsByEmail(String emailId) throws UserDefinedException {

        Employee employee = employeeRepository.findEmployeeByIsdeleteAndAuthEmail(false,emailId).orElseThrow(() -> new UserDefinedException("Employee details not found"));
        EmployeeDetailResponse employeeDetailResponse = new EmployeeDetailResponse() ;
        employeeDetailResponse.setEmail(employee.getAuth().getEmail());
        employeeDetailResponse.setId(employee.getId());
        employeeDetailResponse.setJobTitle(employee.getJob_title());
        employeeDetailResponse.setManagerId(employee.getManagerid());
        employeeDetailResponse.setName(employee.getName());
        employeeDetailResponse.setPhone(employee.getPhone());
        employeeDetailResponse.setRole(employee.getRole().getName());

        return employeeDetailResponse;

    }

    @Override
    public String authLogin(CredentialRequest loginRequest) throws UserDefinedException {
       Login login= loginRepository.findByEmailAndIsdelete(loginRequest.getEmail(),false).orElseThrow(()-> new UserDefinedException("Invalid Email id"));
//       System.out.print(login.getPassword()+""+loginRequest.getPassword());
//       System.out.print(passwordEncoder.matches(loginRequest.getPassword(),login.getPassword()));
        Date date = new Date();
        if (passwordEncoder.matches(loginRequest.getPassword(), login.getPassword())) {
            login.setLastsuccessfullogin(date);
            loginRepository.save(login);
            return "Login successfull";
        } else {
            login.setLastfailedlogin(date);
            login.setNoofAtemptsPasswordFailureCount(login.getNoofAtemptsPasswordFailureCount()+1);
            loginRepository.save(login);
            throw new UserDefinedException("Invalid Password");
        }
    }

    @Override
    public String deleteEmployeeUsingId(int empId) throws UserDefinedException {
        Employee employee =employeeRepository.findById(empId);
        String mailId= employee.getAuth().getEmail();
        if(employee.isIsdelete()){
            throw new UserDefinedException("Employee already Deleted");
        }else {
            Login login = loginRepository.findByEmail(mailId).orElseThrow(()-> new UserDefinedException("Invalid Email id"));
            login.setIsdelete(true);
            employee.setIsdelete(true);
            employeeRepository.save(employee);
            loginRepository.save(login);
            return "Deleted Successfully ";
        }
    }

    @Override
    public String assignEmployeeToManager(AssignEmployeeToManager employee) throws UserDefinedException {
        Employee employeeDetail = employeeRepository.findEmployeeByIsdeleteAndAuthEmail(false, employee.getEmployeeEmail()).orElseThrow(() -> new UserDefinedException("Employee details not found"));

        employeeDetail.setManagerid(employee.getManagerId());

        employeeRepository.save(employeeDetail);

        return "Successfully assigned the employee";
    }

    @Override
    public String addRole(RoleRequest roleRequest) throws UserDefinedException {

        if(roleRepository.existsByName(roleRequest.getName())){throw new UserDefinedException("role Already Exists");}

        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setLevel(roleRequest.getLevel());
        role.setThreshold(roleRequest.getThreshold());

        roleRepository.save(role);
        return "Successfully added the role";
    }

    @Override
    public ArrayList<RoleResponse> getAllRoles() throws UserDefinedException {

        ArrayList<Role> roles = (ArrayList<Role>) roleRepository.findAll();
        if (roles.isEmpty()) {
            throw new UserDefinedException("Roles not found");
        } else {
            ArrayList<RoleResponse> roleResponses = roles.stream()
                    .map(f -> new RoleResponse(f.getName(),f.getLevel()))
                    .collect(Collectors.toCollection(ArrayList::new));
            return roleResponses;
        }
    }


}
