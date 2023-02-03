package com.quinbay.reimbursement.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "claim_category")
@Data
@Getter
@Setter
@NoArgsConstructor
public class ClaimCategory implements Serializable{
    @Id
    @SequenceGenerator(name = "claim_category_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "claim_category_seq")
    private Integer id;

    private String name;

    private String created_by;

    @CreationTimestamp
    private Date created_date;

    private String updated_by;

    @UpdateTimestamp
    private Date updated_date;

    private boolean isdelete = false;
}
