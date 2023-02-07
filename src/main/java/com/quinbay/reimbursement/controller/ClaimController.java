package com.quinbay.reimbursement.controller;


import com.quinbay.reimbursement.api.Claims;
import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.*;
import com.quinbay.reimbursement.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RequestMapping("claim/api/")
@RestController()
public class ClaimController {

    @Autowired
    Claims claimsOp;

    @PostMapping("/addClaimCategory")
    public ResponseEntity<Object> addClaimCategory(@RequestBody ClaimCategory claimCategory) {

        try {
            String result = claimsOp.addClaimCategory(claimCategory);
            return ResponseHandler.generateResponse("Successfully Added claim category", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<Object> getAllCategories() {
        try {
            ArrayList<ClaimCategoryResponse> result = claimsOp.getAllCategories();
            return ResponseHandler.generateResponse("Success got categories", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }


    @PostMapping("/addClaim")
    public ResponseEntity<Object> addClaim(@RequestBody ClaimRequest claim) {

        try {
            Integer result = claimsOp.addClaim(claim);
            return ResponseHandler.generateResponse("Successfully Added claim", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PostMapping("/addImageForClaim")
    public ResponseEntity<Object> addClaimUsingImage(@RequestParam("files") MultipartFile[] files, @RequestParam int claimId) {

        try {
            String result = claimsOp.addClaimUsingImage(files,claimId);
            return ResponseHandler.generateResponse("Successfully Added images", HttpStatus.OK, result);
        } catch (Exception | UserDefinedException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

//    @PostMapping("/addClaimUsingImages")
//    public ResponseEntity<Object> addClaimUsingImages(@RequestBody ClaimImageRequestTest claimImageRequestTest ) {
//
//        try {
//            String result =" "+claimImageRequestTest.getEmployeeid();
////                    claimsOp.addClaimUsingImage(files);
//            return ResponseHandler.generateResponse("Successfully Added claim", HttpStatus.OK, result);
//        } catch (Exception e) {
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
//        }
//    }

    @GetMapping("/getAllClaims")
    public ResponseEntity<Object> getAllClaims() {
        try {
            ArrayList<Claim> result = claimsOp.getAllClaims();
            return ResponseHandler.generateResponse("Success got categories", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @GetMapping("/getClaimsByEmployeeId/{employeeId}")
    public ResponseEntity<Object> getClaimsByEmployeeId(@PathVariable int employeeId,
                                                        @RequestParam(required = false) String status,
                                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "5") Integer pageSize) {
        try {
            ClaimResponseForMultipleUser result = claimsOp.getClaimsByEmployeeId(employeeId,status, pageNo, pageSize);
            return ResponseHandler.generateResponse("Success got employee claims", HttpStatus.OK, result);
        } catch ( Exception |UserDefinedException e ) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @GetMapping("/getClaimDetailsByClaimId/{claimId}")
    public ResponseEntity<Object> getClaimDetailsByClaimId(@PathVariable int claimId) throws UserDefinedException {
        try {
            ClaimDetailResponse result = claimsOp.getClaimDetailsByClaimId(claimId);
            return ResponseHandler.generateResponse("Success got claim details", HttpStatus.OK, result);
        } catch ( Exception | UserDefinedException e ) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @PutMapping("/updateClaimStatus")
    public ResponseEntity<Object> updateClaimStatus(@RequestBody ClaimUpdateRequest claimUpdateRequest ) {

        try {
            String result = claimsOp.updateClaimStatus(claimUpdateRequest);
            return ResponseHandler.generateResponse("Success updated claim", HttpStatus.OK, result);
        } catch ( Exception |UserDefinedException  e ) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @PostMapping("/addComment")
    public ResponseEntity<Object> claimCommentRequest(@RequestBody ClaimCommentRequest claimCommentRequest) {

        try {
            String result = claimsOp.claimCommentRequest(claimCommentRequest);
            return ResponseHandler.generateResponse("Successfully Added Comment", HttpStatus.OK, result);
        } catch (Exception |UserDefinedException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/scheduleMail")
    @Scheduled(cron = "0 0 9 * * *")
    public ResponseEntity<Object> scheduleMail() {

        try {
            String result = claimsOp.scheduleMail();
            return ResponseHandler.generateResponse("Successfully send Mail", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @DeleteMapping("/deleteClaimUsingId/{claimId}")
    public ResponseEntity<Object> deleteClaim(@PathVariable int claimId) {
        try {
            String result = claimsOp.deleteClaimUsingId(claimId);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }

    }


}
