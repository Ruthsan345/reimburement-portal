package com.quinbay.reimbursement.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Data
@Getter
@Setter
@NoArgsConstructor
public class ClaimImageRequestTest {
    private Integer id;

    private int employeeId;

    public int claimCategoryId;

    private String[] imageUrl;

    private Date fromDate;

    private Date toDate;

    private String description;

    private String[] officeStationaryType;

    private int claimAmount;

    private MultipartFile[] files;
}
