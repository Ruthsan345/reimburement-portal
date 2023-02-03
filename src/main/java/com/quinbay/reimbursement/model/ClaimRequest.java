package com.quinbay.reimbursement.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;



@Data
@Getter
@Setter
@NoArgsConstructor
public class ClaimRequest {

    private Integer id;

    private int employeeId;

    private int claimCategoryId;

    private String[] imageUrl;

    private Date fromDate;

    private Date toDate;

    private String description;

    private String[] officeStationaryType;

    private int claimAmount;

}


