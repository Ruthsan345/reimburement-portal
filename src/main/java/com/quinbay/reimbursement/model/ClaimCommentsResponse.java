package com.quinbay.reimbursement.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimCommentsResponse {

    private String comments;

    private Integer employeeId;

    private String employeeName;

    private Date date;
}
