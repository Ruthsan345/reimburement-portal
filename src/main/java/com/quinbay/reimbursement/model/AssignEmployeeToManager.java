package com.quinbay.reimbursement.model;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignEmployeeToManager {
    String employeeEmail;
    int managerId;
}
