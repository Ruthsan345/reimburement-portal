package com.quinbay.reimbursement.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;


@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class ClaimRequest {

    Integer id;

    public int employeeid;

    public int claimCategoryid;

    private String[] image_url;

    public Date from_date;

    public Date to_date;

    public String description;

    public String[] office_stationary_type;

    public int claim_amount;

}


