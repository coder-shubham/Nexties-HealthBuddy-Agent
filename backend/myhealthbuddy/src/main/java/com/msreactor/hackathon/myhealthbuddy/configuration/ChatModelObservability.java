/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;

import static dev.langchain4j.data.message.ChatMessageType.TOOL_EXECUTION_RESULT;
import static dev.langchain4j.data.message.ChatMessageType.USER;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */
@Configuration
public class ChatModelObservability {

    @Bean
    ChatModelListener chatModelListener() {
        return new ChatModelListener() {

            private static final Logger log = LoggerFactory.getLogger(ChatModelListener.class);

            @Override public void onRequest(ChatModelRequestContext requestContext) {
                ChatModelListener.super.onRequest(requestContext);
                List<ChatMessage> requestChatMessagesList = requestContext.chatRequest().messages();
                if(!requestChatMessagesList.isEmpty()){
                    ChatMessage lastMessage = requestChatMessagesList.get(requestChatMessagesList.size() - 1);
                    switch (lastMessage.type()) {
                        case USER -> {
                            UserMessage userMessage = (UserMessage) lastMessage;
                            log.info("User message: {}", userMessage);
                        }
                        case TOOL_EXECUTION_RESULT -> {
                            ToolExecutionResultMessage toolExecutionResultMessage = (ToolExecutionResultMessage) lastMessage;
                            log.info("ToolExecutionResult: ToolName: {} Result: {}",
                                    toolExecutionResultMessage.toolName(), toolExecutionResultMessage.text());
                        }
                        default -> {
                        }
                    }
                }

            }

            @Override public void onResponse(ChatModelResponseContext responseContext) {
                ChatModelListener.super.onResponse(responseContext);
                if(responseContext.chatResponse().aiMessage().hasToolExecutionRequests()){
                   List<ToolExecutionRequest> toolExecutionRequestList =
                           responseContext.chatResponse().aiMessage().toolExecutionRequests();
                     for (ToolExecutionRequest toolExecutionRequest : toolExecutionRequestList) {
                         log.info("Executing tool: {} with arguments: {}",
                                 toolExecutionRequest.name(),
                                 toolExecutionRequest.arguments());
                     }
                }
                log.info("TokenUsage: {}",
                        responseContext.chatResponse().tokenUsage());
            }

            @Override public void onError(ChatModelErrorContext errorContext) {
                ChatModelListener.super.onError(errorContext);
                log.info("onError(): {}", errorContext.error().getMessage());
            }
        };
    }

}
