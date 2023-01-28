package com.quinbay.reimbursement.model;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class ClaimUpdateRequest {
    private Integer claimid;
    private Integer Approver_id;
    private String comment;
    private String status;
}
