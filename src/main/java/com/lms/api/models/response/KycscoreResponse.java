package com.lms.api.models.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class KycscoreResponse implements Serializable
{
    private String id;

    private String customerNumber;

    private String score;

    private String limitAmount;

    private String exclusion;

    private String exclusionReason;
}
