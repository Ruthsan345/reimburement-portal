package com.quinbay.reimbursement.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "role")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String name;

    private Integer level;

    private Integer threshold;

    private String created_by;

    @CreationTimestamp
    private Date created_date;

    private String updated_by;

    @UpdateTimestamp
    private Date updated_date;

    private boolean isdelete=false;
}
