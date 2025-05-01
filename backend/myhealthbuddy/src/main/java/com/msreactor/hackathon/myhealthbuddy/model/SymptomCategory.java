/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.model;

import lombok.Getter;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@Getter
public enum SymptomCategory {
    BASIC("Basic"),
    MODERATE("Moderate"),
    CRITICAL("Critical");

    private final String description;

    SymptomCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
