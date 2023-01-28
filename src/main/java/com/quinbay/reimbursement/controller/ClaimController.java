package com.quinbay.reimbursement.controller;


import com.quinbay.reimbursement.api.Claims;
import com.quinbay.reimbursement.exception.UserDefinedException;
import com.quinbay.reimbursement.model.*;
import com.quinbay.reimbursement.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<Object> getAllCategories() {
        try {
            ArrayList<ClaimCategory> result = claimsOp.getAllCategories();
            return ResponseHandler.generateResponse("Success got categories", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }



    @PostMapping("/addClaim")
    public ResponseEntity<Object> addClaim(@RequestBody ClaimRequest claim) {

        try {
            String result = claimsOp.addClaim(claim);
            return ResponseHandler.generateResponse("Successfully Added claim", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/addClaimUsingImage")
    public ResponseEntity<Object> addClaimUsingImage(@RequestParam List<MultipartFile> files ,@RequestBody ClaimRequest claimRequest ) {

        try {
            String result = claimsOp.addClaimUsingImage(claimRequest, files);
            return ResponseHandler.generateResponse("Successfully Added claim", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/getAllClaims")
    public ResponseEntity<Object> getAllClaims() {
        try {
            ArrayList<Claim> result = claimsOp.getAllClaims();
            return ResponseHandler.generateResponse("Success got categories", HttpStatus.OK, result);
        } catch (UserDefinedException | Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @GetMapping("/getClaimsByEmployeeId/{employeeid}")
    public ResponseEntity<Object> getClaimsByEmployeeId(@PathVariable int employeeid, @RequestParam String status, @RequestParam String role) {
        try {
            ArrayList<ClaimResponse> result = claimsOp.getClaimsByEmployeeId(employeeid,status,role);
            return ResponseHandler.generateResponse("Success got employee claims", HttpStatus.OK, result);
        } catch ( Exception |UserDefinedException e ) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    @PutMapping("/updateClaimStatus")
    public ResponseEntity<Object> updateClaimStatus(@RequestBody ClaimUpdateRequest claimUpdateRequest ) {

        try {
            String result = claimsOp.updateClaimStatus(claimUpdateRequest);
            return ResponseHandler.generateResponse("Success updated claim", HttpStatus.OK, result);
        } catch ( Exception  e ) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

}
