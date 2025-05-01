/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 01/05/25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class WebSocketPayload {

    private String content;
    private String requestMessageType;
    private String chatId;
    private String messageId;
    private String type;
    private String sender;
    private long timestamp;
}
