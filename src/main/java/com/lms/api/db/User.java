package com.lms.api.db;


import com.lms.api.services.soapservices.customer.Gender;
import com.lms.api.services.soapservices.customer.IdType;
import com.lms.api.services.soapservices.customer.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("customer")
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column("id")
    private String id;

    @Column("customerNumber")
    private String customerNumber; //phonenumber

    @Column("dob")
    private LocalDate dob;

    @Column("email")
    private String email;

    @Column("firstName")
    private String firstName;

    @Column("gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column("idNumber")
    private String idNumber;

    @Column("idType")
    @Enumerated(EnumType.STRING)
    private IdType idType;

    @Column("lastName")
    private String lastName;

    @Column("middleName")
    private String middleName;

    @Column("mobile")
    private String mobile;

    @Column("monthlyIncome")
    private double monthlyIncome;

    @Column("status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column("kycScoretoken")
    private String kycScoretoken;
}
