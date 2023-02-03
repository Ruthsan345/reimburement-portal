package com.quinbay.reimbursement.model;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailRequest {

    private String name;

    private Double phone;

    private String email;

    private String jobTitle;

    private String role;

    private Integer managerId;

    private String password;
}
