package com.quinbay.reimbursement.model;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ClaimUpdateRequest {
    private Integer claimId;
    private Integer ApproverId;
    private Integer approvedClaimAmount;
    private String status;
}
