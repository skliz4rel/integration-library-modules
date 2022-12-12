package com.lms.api.db;

import lombok.Data;
import lombok.Generated;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Indexed;

@Data
@Table("token")
public class Token {

    @Id
    @Generated
    @Column("id")
    protected String id;

    @Column("username")
    protected String username;

    @Column("hashPassword")
    protected String hashPassword; //bcrypt encryption.

    @Column("basicAuthstr")
    protected String basicAuthstr;

    @Column("scoringEngineToken")
    protected String scoringEngineToken;
}