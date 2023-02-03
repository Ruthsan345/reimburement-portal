package com.quinbay.reimbursement.model;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimCategoryResponse {
    private Integer id;

    private String name;
}
