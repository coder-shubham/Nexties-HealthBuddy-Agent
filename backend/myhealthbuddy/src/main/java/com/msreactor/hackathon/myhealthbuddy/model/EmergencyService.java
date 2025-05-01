/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.model;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */
public record EmergencyService(
        Integer id,
        String name,
        String location,
        String contactNumber,
        String serviceType,
        String serviceDescription
) {
    // Constructor, getters, and other methods can be added here if needed
}
