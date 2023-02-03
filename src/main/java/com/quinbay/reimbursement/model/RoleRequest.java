package com.quinbay.reimbursement.model;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    private String name;

    private Integer level;

    private Integer threshold;

}
