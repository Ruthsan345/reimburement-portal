package com.quinbay.reimbursement.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private String name;

    private Integer level;
}
