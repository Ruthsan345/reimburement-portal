package com.quinbay.reimbursement.service.impl;


import com.quinbay.reimbursement.api.Claims;
import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.kafka.KafkaPublishService;
import com.quinbay.reimbursement.model.*;
import com.quinbay.reimbursement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    KafkaPublishService kafkaPublishService;


    @Override
    public String addClaimCategory(ClaimCategory claimCategory) {
        claimCategoryRepository.save(claimCategory);
        return "Claim Category added";
    }

    @Override
    public ArrayList<ClaimCategoryResponse> getAllCategories() throws UserDefinedException {
        ArrayList<ClaimCategory> claimCategories = (ArrayList<ClaimCategory>) claimCategoryRepository.findAll();

        if (claimCategories.isEmpty()) {
            throw new UserDefinedException("Categories not found");
        } else {
            ArrayList<ClaimCategoryResponse> claimCategoryResponses = claimCategories.stream()
                    .map(f -> new ClaimCategoryResponse(f.getId(), f.getName()))
                    .collect(Collectors.toCollection(ArrayList::new));
            return claimCategoryResponses;
        }
    }
    public int getfinancierIdBasedOnRequest(String role){
        ArrayList<Employee> employeesIfFinancier = employeeRepository.findByRoleName(role);
        int value;
        HashMap<Integer, Integer> employeeCountMap = new HashMap<>();
        for(Employee employee : employeesIfFinancier){
            value = claimApprovalRepository.countByApproveridAndStatus(employee.getId(),"PENDING");
            employeeCountMap.put(employee.getId(), value);
        }
        int maxKey = employeeCountMap.keySet().stream()
                .min(Comparator.comparing(employeeCountMap::get))
                .orElse(null);
        return maxKey;
        }

    @Override
    public Integer addClaim(ClaimRequest claimRequest) throws UserDefinedException {
            Claim claims = new Claim();Integer managerId;
            ClaimCategory cat = claimCategoryRepository.findByIdAndIsdelete(claimRequest.getClaimCategoryId(), false);
            Employee employee = employeeRepository.findByIdAndIsdelete(claimRequest.getEmployeeId(), false).orElseThrow(()-> new UserDefinedException("Employee not found"));
            claims.setEmployee(employee);
            claims.setClaimCategory(cat);
            claims.setDescription(claimRequest.getDescription());
            claims.setClaim_amount(claimRequest.getClaimAmount());
            claims.setFrom_date(claimRequest.getFromDate());
            claims.setTo_date(claimRequest.getToDate());
            claims.setOffice_stationary_type(claimRequest.getOfficeStationaryType());
            claims.setImage_url(claimRequest.getImageUrl());
            Claim claim = claimRepository.save(claims);
            if(employeeRepository.existsById(employee.getManagerid())) {
                Employee managerDetails = employeeRepository.findById(employee.getManagerid()).orElseThrow(()-> new UserDefinedException("Manager not found"));
                if(managerDetails.getRole().getThreshold()<claimRequest.getClaimAmount()){
                    managerId = managerDetails.getManagerid();
                }else{ managerId=employee.getManagerid(); }
            }
            else{ managerId = employee.getManagerid(); }
            ClaimApproval claimApproval = new ClaimApproval(claim,managerId,  1, "PENDING");
            ClaimApproval claimApproval1 = new ClaimApproval(claim,getfinancierIdBasedOnRequest("FINANCIER"),2,"PENDING");
            claimApprovalRepository.save(claimApproval);
            claimApprovalRepository.save(claimApproval1);
        return claim.getId();
    }

    @Override
    public ArrayList<Claim> getAllClaims() throws UserDefinedException {
        ArrayList<Claim> claims = claimRepository.findByIsdelete(false);
        if (claims.isEmpty()) {
            throw new UserDefinedException("claims not found");
        } else {
            return claims;
        }
    }

    @Override
    public String addClaimUsingImage(MultipartFile[] files, int claimId) throws UserDefinedException {
        List<String> fileNames = new ArrayList<>();
        Arrays.asList(files).stream().forEach(file -> {
            try {
                String fileName = "images/" + System.currentTimeMillis() + ".jpg";
                byte[] bytes = file.getBytes();
//                bytes =
                Path path = Paths.get("./src/main/resources/static/" + fileName);
                Files.write(path, bytes);
                fileNames.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        String[] image_url = new String[fileNames.size()];
        for (int i = 0; i < fileNames.size(); i++) {
            image_url[i] = fileNames.get(i);
        }
        Claim claim = claimRepository.findByIdAndIsdelete(claimId,false).orElseThrow(()-> new UserDefinedException("Claim not found"));
        claim.setImage_url(image_url);
        claimRepository.save(claim);
        return "Successfully added the image for claim id : "+claimId;
    }

    public ArrayList<Claim> getClaimsByStatus(ArrayList<Claim> claims, String status){
        ArrayList<Claim> claimByStatus = claims.stream()
                .filter(claim -> claim.claimApprovals.stream()
                        .anyMatch(approval -> approval.getStatus().equals(status)))
                .collect(Collectors.toCollection(ArrayList::new));
        return claimByStatus;
    }

    public ArrayList<ClaimResponse> getClaimResponseFromClaim(ArrayList<Claim> claims){
        ArrayList<ClaimResponse> claimResponse = new ArrayList<ClaimResponse>();
        for (Claim claim : claims) {
            ArrayList<ClaimApprovalResponse> claimApprovalResponses = claim.getClaimApprovals().stream()
                    .map(f -> new ClaimApprovalResponse(f.getApproverid(), f.getLevel(), f.getStatus()))
                    .sorted(Comparator.comparing(ClaimApprovalResponse::getLevel))
                    .collect(Collectors.toCollection(ArrayList::new));
            ClaimResponse response = new ClaimResponse(claim.getId(), claim.getEmployee().getName(), claim.getClaimCategory().getName(), claim.getClaim_amount(), claim.getDescription(), claimApprovalResponses);
            claimResponse.add(response);
        }
        return  claimResponse;
    }

    public ArrayList<ClaimResponse> getClaimFromApproverId(int approverId, String status, Pageable paging){
        ArrayList<ClaimApproval> claimApprovals = claimApprovalRepository.findByApproveridAndIsdelete(approverId, false, paging);
        ArrayList<Claim> claimsByApprovalId = claimApprovals.stream().map(f-> f.getClaim()).collect(Collectors.toCollection(ArrayList::new));
           if(status==null){
               return getClaimResponseFromClaim(claimsByApprovalId);
           }else{
               return getClaimResponseFromClaim(getClaimsByStatus(claimsByApprovalId,status));
           }
    }

    @Override
    public ClaimResponseForMultipleUser getClaimsByEmployeeId(int employeeid, String status, Integer pageNo, Integer pageSize) throws UserDefinedException {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Employee employee = employeeRepository.findByIdAndIsdelete(employeeid, false).orElseThrow(()-> new UserDefinedException("Employee not found"));
        ArrayList<Claim> claims = claimRepository.findByIsdeleteAndEmployeeId(false,employeeid, paging);
        ClaimResponseForMultipleUser claimResponseForMultipleUser = new ClaimResponseForMultipleUser();
        ArrayList<ClaimResponse> claimResponses = new ArrayList<ClaimResponse>();
        if(status==null) {
                    claimResponseForMultipleUser.setMyClaims(getClaimResponseFromClaim(claims));
           } else{
                    claimResponseForMultipleUser.setMyClaims(getClaimResponseFromClaim(getClaimsByStatus(claims, status)));
            }
                claimResponses.addAll(getClaimFromApproverId(employeeid, status,paging));
                claimResponses = getUniqueClaimResponses(claimResponses);
                claimResponseForMultipleUser.setEmployeeClaims(claimResponses);
            return claimResponseForMultipleUser;
        }


    private ArrayList<ClaimResponse> getUniqueClaimResponses(ArrayList<ClaimResponse> claimResponses) {
        return claimResponses.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public String updateClaimStatus(ClaimUpdateRequest claimUpdateRequest) throws UserDefinedException {
        int empid = claimUpdateRequest.getApproverId();
        ArrayList<ClaimApproval> claimApproval = claimApprovalRepository.findByClaimId(claimUpdateRequest.getClaimId()).orElseThrow(()-> new UserDefinedException("claim id not found"));
        if(claimApproval.isEmpty()){throw new UserDefinedException("claim id not found");}
        Employee employee = employeeRepository.findByIdAndIsdelete(empid, false).orElseThrow(()-> new UserDefinedException("Employee not found"));;
        if (employee.getRole().equals("EMPLOYEE")) {throw new UserDefinedException("Employee cannot update status");}
        for (ClaimApproval ca : claimApproval) {
            if(ca.getApproverid() == claimUpdateRequest.getApproverId() ){
                if (ca.getLevel() == 1 && claimApproval.stream().anyMatch(app -> app.getLevel() == 2 && app.getStatus().equals("APPROVED") || app.getStatus().equals("REJECTED"))) {
                   throw new UserDefinedException("Cannot update status, financier has already approved/Rejected, ");
                }if (ca.getLevel() == 2 && claimApproval.stream().anyMatch(app -> app.getLevel() == 1 && app.getStatus().equals("PENDING"))) {
                    throw new UserDefinedException("Cannot update status, wait for Manager approval. Manager will be notified");
                }
                else {
                    if(ca.getLevel()==1 && claimUpdateRequest.getStatus().equals("REJECTED") ){
                        String status = claimUpdateRequest.getStatus();
                        claimApproval.stream().filter(app -> app.getLevel() == 2).forEach(app -> app.setStatus(status));
                    }
                    ca.setApproved_Amount(claimUpdateRequest.getApprovedClaimAmount());
                    ca.setStatus(claimUpdateRequest.getStatus());
                    claimApprovalRepository.save(ca);
                    Mailer mail =new Mailer(ca.getClaim().getEmployee().getAuth().getEmail(),"Your Claim number : "+claimUpdateRequest.getClaimId()+". has been "+claimUpdateRequest.getStatus()+".");
                    kafkaPublishService.sendMailingInformation(mail);
                    return "claim request : " + claimUpdateRequest.getClaimId() + " is " + claimUpdateRequest.getStatus();
                }
            }
        }
        return "Error updating the status.";
    }

    @Override
    public ClaimDetailResponse getClaimDetailsByClaimId(int claimId) throws UserDefinedException {
        Claim claimDetail = claimRepository.findByIdAndIsdelete(claimId, false).orElseThrow(()-> new UserDefinedException("Claim not found"));
        List<ClaimComment> claimComments =  claimDetail.getClaimComment();
        ArrayList<ClaimApprovalResponse> claimApprovalResponses = new ArrayList<ClaimApprovalResponse>();
        ClaimDetailResponse response = new ClaimDetailResponse(claimDetail.getId(),claimDetail.getEmployee().getName(),claimDetail.claimCategory.getName(),claimDetail.getImage_url(),claimDetail.getFrom_date(),claimDetail.getTo_date(),claimDetail.getOffice_stationary_type(),claimDetail.getDescription(),claimDetail.getClaim_amount(),claimDetail.getCreated_date());
        ArrayList<ClaimCommentsResponse> claimCommentsResponse = new ArrayList<ClaimCommentsResponse>();
        for (ClaimComment cc : claimComments) {
            ClaimCommentsResponse claimcomm = new ClaimCommentsResponse(cc.getComments(),cc.getEmployeeid(),employeeRepository.findByIdAndIsdelete(cc.getEmployeeid(), false).orElseThrow(()-> new UserDefinedException("Employee not found")).getName(),cc.getDate());
            claimCommentsResponse.add(claimcomm);
        }
        response.setClaimCommentsResponses(claimCommentsResponse);
        ArrayList<ClaimApproval> claimApproval = claimApprovalRepository.findByClaimId(claimId).orElseThrow(()-> new UserDefinedException("claim id not found"));
        for (ClaimApproval ca : claimApproval) {
            ClaimApprovalResponse car = new ClaimApprovalResponse(ca.getApproverid(),ca.getLevel(),ca.getStatus());
            claimApprovalResponses.add(car);
        }
        response.setStatusOfApprovers(claimApprovalResponses);
        return response;
    }

    @Override
    public String claimCommentRequest(ClaimCommentRequest claimCommentRequest) throws UserDefinedException {
        Claim claim = claimRepository.findById(claimCommentRequest.getClaimId()).orElseThrow(()-> new UserDefinedException("claim id not found"));;
        ClaimComment claimComment = new ClaimComment();
        claimComment.setClaim(claim);
        claimComment.setComments(claimCommentRequest.getComments());
        employeeRepository.findByIdAndIsdelete(claimCommentRequest.getEmployeeId(),false).orElseThrow(()-> new UserDefinedException("employee not found"));
        claimComment.setEmployeeid(claimCommentRequest.getEmployeeId());
        claimCommentRepository.save(claimComment);
        Mailer mailer = new Mailer(claim.getEmployee().getAuth().getEmail(), "A New comment is added to your claim no :" + claimCommentRequest.getClaimId());
        kafkaPublishService.sendMailingInformation(mailer);
        return "comment added to the claim sucessfully";
    }

    @Override
    public String scheduleMail() throws UserDefinedException {
        ArrayList<ClaimApproval> claimApproval = claimApprovalRepository.findByStatus("PENDING");
        try {
            List<Mailer> mailerList = new ArrayList<Mailer>();
            for (ClaimApproval ca : claimApproval) {
                if (ca.getApproverid() != null) {
                    int approverId = ca.getApproverid();
                    Employee emp = employeeRepository.findByIdAndIsdelete(approverId, false).orElseThrow(()-> new UserDefinedException("Employee not found"));
                    String empMail = emp.getAuth().getEmail();
                    Mailer mailInfo = new Mailer(empMail,"You have a pending claim to review \n Claim id is : " + ca.getClaim().getId());
                    mailerList.add(mailInfo);
                }
            }
            kafkaPublishService.sendScheduledMail(mailerList);
        } catch (Exception e) {
            System.out.print(e);
        }
        return "Success";
    }

    @Override
    public String deleteClaimUsingId(int claimId) throws UserDefinedException {
        Claim claimDetail = claimRepository.findByIdAndIsdelete(claimId, false).orElseThrow(()-> new UserDefinedException("Claim not found"));
        try {
                if (claimDetail.isIsdelete()) {
                    throw new UserDefinedException("Claim already deleted ");
                } else {
                    ArrayList<ClaimApproval> claimApprovals = claimApprovalRepository.findByClaimId(claimId).orElseThrow(()-> new UserDefinedException("claim id not found"));
                    for (ClaimApproval ca : claimApprovals) {
                        ca.setIsdelete(true);
                        claimApprovalRepository.save(ca);
                    }
                    claimDetail.setIsdelete(true);
                    claimRepository.save(claimDetail);
                    return "Deleted Successfully ";
                }
            }
        catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

}
