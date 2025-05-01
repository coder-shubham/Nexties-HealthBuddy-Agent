/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import lombok.Data;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 01/05/25
 */

@Configuration
@Data
public class AppConfig {

    @Value("${azure.cognitive-services.api-key}")
    private String speechSubscriptionKey;

    @Value("${azure.cognitive-services.region}")
    private String speechRegion;
}
