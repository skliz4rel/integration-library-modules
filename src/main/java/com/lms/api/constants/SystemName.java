package com.lms.api.constants;

import lombok.Getter;

/**
 * This model would be used to store all the APIS we integrated too.
 * OR you can call it systems.
 * */

@Getter
public enum SystemName {
    LMS("LMS", "Loan Management Service"),
    MIDDLEWARE("MIDDLEWARE", "Middleware Service");

    private final String name;
    private final String description;

    SystemName(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
