package com.quinbay.reimbursement.service.impl;


import com.quinbay.reimbursement.api.Claims;
import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.*;
import com.quinbay.reimbursement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ClaimOperation implements Claims {

    @Autowired
    ClaimCategoryRepository claimCategoryRepository;

    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ClaimApprovalRepository claimApprovalRepository;

    @Autowired
    ClaimCommentRepository claimCommentRepository;

    java.sql.Date date;
    long millis=System.currentTimeMillis();

    @Override
    public String addClaimCategory(ClaimCategory claimCategory) {
        claimCategoryRepository.save(claimCategory);

        return "Claim Category added";
    }

    @Override
    public ArrayList<ClaimCategory> getAllCategories() throws UserDefinedException {

        ArrayList<ClaimCategory> claimCategories = (ArrayList<ClaimCategory>) claimCategoryRepository.findAll();
        if(claimCategories.isEmpty()){
            throw new UserDefinedException("Categories not found");
        }else{
            return claimCategories;
        }
    }

    @Override
    public String addClaim(ClaimRequest claimRequest) {
        Claim claims = new Claim();
        try {
            ClaimCategory cat = claimCategoryRepository.findById(claimRequest.getClaimCategoryid());
            Employee employee = employeeRepository.findById(claimRequest.getEmployeeid());
            claims.setEmployee(claimRequest.getEmployeeid());
            claims.setClaimCategory(cat);
            claims.setDescription(claimRequest.getDescription());
            claims.setClaim_amount(claimRequest.getClaim_amount());
            claims.setFrom_date(claimRequest.getFrom_date());
            claims.setTo_date(claimRequest.getTo_date());
            claims.setOffice_stationary_type(claimRequest.getOffice_stationary_type());
            claims.setImage_url(claimRequest.getImage_url());
            Claim claim =claimRepository.save(claims);

            ClaimApproval claimApproval = new ClaimApproval();
            ClaimApproval claimApproval1 = new ClaimApproval();

            claimApproval.setApproverid(employee.getManagerid());
            claimApproval.setClaimid(claim.getId());
            claimApproval.setLevel(1);
            claimApproval.setStatus("PENDING");

            claimApproval1.setClaimid(claim.getId());
            claimApproval1.setLevel(2);
            claimApproval1.setStatus("PENDING");

            claimApprovalRepository.save(claimApproval);
            claimApprovalRepository.save(claimApproval1);


        }catch (Exception e){
            System.out.print(e);
        }
        return "Claim added successfully";
    }

    @Override
    public ArrayList<Claim> getAllClaims() throws UserDefinedException {
        ArrayList<Claim> claims = (ArrayList<Claim>) claimRepository.findAll();
        if(claims.isEmpty()){
            throw new UserDefinedException("claims not found");
        }else{
            return claims;
        }
    }

    @Override
    public String addClaimUsingImage(ClaimRequest claimRequest, List<MultipartFile> files) throws IOException {

        String[] image_url={};
        String arrNew[] = Arrays.copyOf(image_url, image_url.length + 1);
        int i =0;

        for (MultipartFile file : files) {
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'.jpg'").format(new Date());
            byte[] bytes = file.getBytes();
            Path path = Paths.get("./image/" + fileName);
            Files.write(path, bytes);
            image_url[i]= fileName;
            i++;
        }
        for(String s: image_url)
            System.out.print("\n\n------------------add claim using"+s);
        return null;
    }

    @Override
    public ArrayList<ClaimResponse> getClaimsByEmployeeId(int employeeid, String status, String role) throws UserDefinedException {
        ArrayList<ClaimResponse> claimResponse = new ArrayList<ClaimResponse>();
        if(role.equals("EMPLOYEE")){
//           Employee employee = employeeRepository.findById(employeeid);
           ArrayList<Claim> claim = claimRepository.findByEmployee(employeeid);

            for(Claim c: claim){
                ClaimResponse response =  new ClaimResponse();

                ArrayList<ClaimApprovalResponse> claimApprovalResponses = new ArrayList<ClaimApprovalResponse>();
               ArrayList<ClaimApproval> claimApproval= claimApprovalRepository.findByClaimid(c.getId());

              for(ClaimApproval ca: claimApproval){
                  ClaimApprovalResponse car = new ClaimApprovalResponse();
                  car.setApproverid(ca.getApproverid());
                  car.setLevel(ca.getLevel());
                  car.setStatus(ca.getStatus());
                  claimApprovalResponses.add(car);
                  if(status.equals("PENDING")) {
                      System.out.print(ca.status);
                      if (ca.getStatus().equals(status)) {
                          System.out.print(ca.status);
                          response.setClaimId(c.getId());
                          response.setCategory(c.claimCategory.getName());
                          response.setDescription(c.getDescription());
                          response.setAmount(c.getClaim_amount());
                          response.setStatusOfApprovers(claimApprovalResponses);
                      }
                  }
                  if(status.equals("APPROVED")){
                      if(ca.getLevel()==2 && ca.getStatus().equals("APPROVED")){
                          response.setClaimId(c.getId());
                          response.setCategory(c.claimCategory.getName());
                          response.setDescription(c.getDescription());
                          response.setAmount(c.getClaim_amount());
                          response.setStatusOfApprovers(claimApprovalResponses);
                      }
                  }
                  if(status.equals( "REJECTED")){
                      if(ca.getStatus().equals("REJECTED")){
                          response.setClaimId(c.getId());
                          response.setCategory(c.claimCategory.getName());
                          response.setDescription(c.getDescription());
                          response.setAmount(c.getClaim_amount());
                          response.setStatusOfApprovers(claimApprovalResponses);
                      }
                  }
              }
                if(response.getClaimId()!=null){
                    claimResponse.add(response);
                }
           }


        }
        else if(role.equals("MANAGER")){
            Employee employee = employeeRepository.findById(employeeid);
           if( employee.getRole().equals("MANAGER")) {
               ArrayList<Employee> employees = employeeRepository.findByManagerid(employeeid);

               for(Employee e: employees){
                   ArrayList<Claim> claim = claimRepository.findByEmployee(e.getId());


                   for(Claim c: claim){
                       ClaimResponse response =  new ClaimResponse();

                       ArrayList<ClaimApprovalResponse> claimApprovalResponses = new ArrayList<ClaimApprovalResponse>();
                       ArrayList<ClaimApproval> claimApproval= claimApprovalRepository.findByClaimid(c.getId());

                       for(ClaimApproval ca: claimApproval){
                           ClaimApprovalResponse car = new ClaimApprovalResponse();
                           car.setApproverid(ca.getApproverid());
                           car.setLevel(ca.getLevel());
                           car.setStatus(ca.getStatus());
                           claimApprovalResponses.add(car);
                           if(status.equals("PENDING")) {
                               System.out.print(ca.status);
                               if (ca.getStatus().equals(status)) {
                                   System.out.print(ca.status);
                                   response.setClaimId(c.getId());
                                   response.setEmployeeName(e.getName());
                                   response.setCategory(c.claimCategory.getName());
                                   response.setDescription(c.getDescription());
                                   response.setAmount(c.getClaim_amount());
                                   response.setStatusOfApprovers(claimApprovalResponses);
                               }
                           }
                           if(status.equals("APPROVED")){
                               if(ca.getLevel()==2 && ca.getStatus().equals("APPROVED")){
                                   response.setClaimId(c.getId());
                                   response.setCategory(c.claimCategory.getName());
                                   response.setDescription(c.getDescription());
                                   response.setEmployeeName(e.getName());
                                   response.setAmount(c.getClaim_amount());
                                   response.setStatusOfApprovers(claimApprovalResponses);
                               }
                           }
                           if(status.equals( "REJECTED")){
                               if(ca.getStatus().equals("REJECTED")){
                                   response.setClaimId(c.getId());
                                   response.setCategory(c.claimCategory.getName());
                                   response.setDescription(c.getDescription());
                                   response.setEmployeeName(e.getName());
                                   response.setAmount(c.getClaim_amount());
                                   response.setStatusOfApprovers(claimApprovalResponses);
                               }
                           }
                       }
                       if(response.getClaimId()!=null){
                           claimResponse.add(response);
                       }
                   }

               }

           }else{
               throw new UserDefinedException("You're not a manager");
           }
        }
        else if(role.equals("FINANCIER")){
            Employee employee = employeeRepository.findById(employeeid);
            if( employee.getRole().equals("FINANCIER")) {


                ArrayList<Claim> claim = (ArrayList<Claim>) claimRepository.findAll();

                for(Claim c: claim){
                    ClaimResponse response =  new ClaimResponse();
                    int id =c.getEmployee();
                    Employee emp = employeeRepository.findById(id);

                    ArrayList<ClaimApprovalResponse> claimApprovalResponses = new ArrayList<ClaimApprovalResponse>();
                    ArrayList<ClaimApproval> claimApproval= claimApprovalRepository.findByClaimid(c.getId());

                    for(ClaimApproval ca: claimApproval){
                        ClaimApprovalResponse car = new ClaimApprovalResponse();
                        car.setApproverid(ca.getApproverid());
                        car.setLevel(ca.getLevel());
                        car.setStatus(ca.getStatus());
                        claimApprovalResponses.add(car);
                        if(status.equals("PENDING")) {
                            System.out.print(ca.status);
                            if (ca.getLevel()==1 && ca.getStatus().equals("APPROVED")) {
                                System.out.print(ca.status);
                                response.setClaimId(c.getId());
                                response.setEmployeeName(emp.getName());
                                response.setCategory(c.claimCategory.getName());
                                response.setDescription(c.getDescription());
                                response.setAmount(c.getClaim_amount());
                                response.setStatusOfApprovers(claimApprovalResponses);
                            }
                        }
                        if(status.equals("APPROVED")){
                            if(ca.getLevel()==2 && ca.getStatus().equals("APPROVED")){
                                response.setClaimId(c.getId());
                                response.setCategory(c.claimCategory.getName());
                                response.setEmployeeName(emp.getName());
                                response.setDescription(c.getDescription());
                                response.setAmount(c.getClaim_amount());
                                response.setStatusOfApprovers(claimApprovalResponses);
                            }
                        }
                        if(status.equals( "REJECTED")){
                            if(ca.getStatus().equals("REJECTED")){
                                response.setClaimId(c.getId());
                                response.setCategory(c.claimCategory.getName());
                                response.setEmployeeName(emp.getName());
                                response.setDescription(c.getDescription());
                                response.setAmount(c.getClaim_amount());
                                response.setStatusOfApprovers(claimApprovalResponses);
                            }
                        }
                    }
                    if(response.getClaimId()!=null){
                        claimResponse.add(response);
                    }
                }


            }
            else{
                throw new UserDefinedException("You're not a Financier");
            }


        }

        return claimResponse;
    }

    @Override
    public String updateClaimStatus(ClaimUpdateRequest claimUpdateRequest) {
        date=new java.sql.Date(millis);
        int empid= claimUpdateRequest.getApprover_id();
        Employee employee = employeeRepository.findById(empid);
//        ArrayList<ClaimApproval> claimApproval= claimApprovalRepository.findByApproverid(empid);
        ArrayList<ClaimApproval> claimApproval= claimApprovalRepository.findByClaimid(claimUpdateRequest.getClaimid());
        for(ClaimApproval ca: claimApproval){
            System.out.print(ca.getLevel()+"---------------->");
            if(ca.getApproverid()==empid) {
                System.out.print(ca.status+"---check 1------------->");

                ca.setStatus(claimUpdateRequest.getStatus());
            }
            else {
                System.out.print(ca.status+"------check 2---------->");

                ca.setStatus(claimUpdateRequest.getStatus());
            }
            System.out.print(ca.status+"-------check 3--------->");
            ClaimApproval claimApproval2 = claimApprovalRepository.save(ca);

        }

        try {
            ClaimComments claimComments = new ClaimComments();

            claimComments.setClaimid(claimUpdateRequest.getClaimid());
            claimComments.setComments(claimUpdateRequest.getComment());
            claimComments.setEmployeeid(empid);
            claimComments.setDate(date);

            ClaimComments res = claimCommentRepository.save(claimComments);

            System.out.print(res.getId() + "" + res + "------------->");
        }
        catch (Exception e){
            System.out.print(e);
        }
        return "claim request : "+claimUpdateRequest.getClaimid()+" is "+claimUpdateRequest.getStatus();
    }
}
