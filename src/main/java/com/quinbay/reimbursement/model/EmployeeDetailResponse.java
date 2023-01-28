package com.quinbay.reimbursement.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailResponse {
    Integer id;
    public String name;
    public Double phone;
    public String email;
    public String job_title;
    public String role;
    public int managerid;
}
