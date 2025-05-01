/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.msreactor.hackathon.myhealthbuddy.agent.HealthBuddyAgent;
import com.msreactor.hackathon.myhealthbuddy.model.WebSocketPayload;

import dev.langchain4j.service.Result;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 01/05/25
 */
@Service
public class ChatService {

    @Autowired
    public HealthBuddyAgent healthBuddyAgent;

    @Autowired
    public SimpMessagingTemplate messagingTemplate;

    private ExecutorService executorService;

    public ChatService() {
        this.executorService = null;
    }

    @PostConstruct
    public void init() throws Exception{
        executorService = Executors.newFixedThreadPool(10);
    }

    @Async
    public void startChat(String chatId) throws Exception {

        System.out.println("Starting chat with ID: " + chatId);

        String aiResponse = getAIResponse(chatId, true,
                "", "");
        String messageId = String.valueOf(System.currentTimeMillis());

        sendMessageToWebSocket(aiResponse, messageId, chatId);

    }


    @Async
    public void processUserMessage(String chatId, String messageId, String userMessage) throws Exception {
        // Process the user message and get AI response
        System.out.println("Processing user message with ID: " + messageId + " for chatId ID: " + chatId);
        // Convert audio file to text
//        String message = healthBuddySpeechService.covertSpeechToText(audioFile.getBytes());

        System.out.println("User Message: " + userMessage);

        String aiResponse = getAIResponse(chatId, false, userMessage, messageId);

        sendMessageToWebSocket(aiResponse, messageId, chatId);

    }


    public String getAIResponse(String callId, Boolean newSession,
                              String message, String messageId) {


        if(newSession) {
            // Start a new session
            message = "FROM SYSTEM: Starting a new session with callId: " + callId;
            messageId = String.valueOf(System.currentTimeMillis());
        }

        Result<String> aiAnswer = healthBuddyAgent.answer(callId, message);

        System.out.println("AI Answer: " + aiAnswer.content());

        return aiAnswer.content();
    }

    private void sendMessageToWebSocket(String aiResponse, String messageId, String chatId) throws Exception {
        WebSocketPayload webhookMessagePayload = new WebSocketPayload()
                .setMessageId(messageId)
                .setChatId(chatId)
                .setContent(aiResponse)
                .setRequestMessageType("text")
                .setType("text")
                .setSender("ai")
                .setTimestamp(System.currentTimeMillis());

        executorService.submit(() -> {
            // Send the message to the WebSocket
            executorService.submit(new MessageTask(messagingTemplate, webhookMessagePayload));
        });
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {

        if (executorService != null) {
            executorService.shutdown();
            executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        }
    }

}
