package com.quinbay.reimbursement.model;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailResponse {
    private Integer id;
    private String name;
    private Double phone;
    private String email;
    private String jobTitle;
    private String role;
    private int managerId;
}
