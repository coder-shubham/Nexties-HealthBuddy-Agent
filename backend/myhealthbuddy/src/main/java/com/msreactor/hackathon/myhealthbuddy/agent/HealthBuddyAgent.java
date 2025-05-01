/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.agent;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@AiService
public interface HealthBuddyAgent {

    @SystemMessage(fromResource = "health_buddy_system_message_with_tool.txt")
    Result<String> answer(@MemoryId String memoryId, @UserMessage String userMessage);

}
