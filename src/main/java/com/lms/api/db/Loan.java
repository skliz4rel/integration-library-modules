package com.lms.api.db;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Table("loan")
@Data
public class Loan {

    @Id
    @Column("id")
    private String id;

    @Column("loanId")
    private String loanId;

    @Column("createdDate")
    private LocalDate createdDate;

    @Column("createdTime")
    private LocalTime createdTime;

    @Column("requestedAmount")
    private double requestedAmount;

    @Column("interest")
    private double interest;

    @Column("paybackAmount")
    private double paybackAmount;

    @Column("creditscore")
    private  String creditscore;

    @Column("paybackDate")
    private LocalDate paybackDate;

    @Column("isActive")
    private boolean isActive;

    @Column("isPaidback")
    private boolean isPaidback;

    @Column("customerNumber")
    private String customerNumber;
}
