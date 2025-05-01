/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msreactor.hackathon.myhealthbuddy.service.ChatService;

/**
 * Class Description goes here.
 * Created by shubhamsarraf on 30/04/25
 */

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity<?> startChat() {
        // Logic to start a chat session
        try {
            String chatId = String.valueOf(System.currentTimeMillis());

            System.out.println("Starting chat with ID: " + chatId);

            chatService.startChat(chatId);

            return ResponseEntity.ok(Map.of("chatId", chatId));
        } catch (Exception e) {
            System.err.println("Error starting call: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start the call.");
        }
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    private ResponseEntity<?> userChatMessage(
            @RequestBody  Map<String, Object> payload
    ) {
        try {
            // Extracting message from the payload
            String message = (String) payload.get("content");
            String chatId = (String) payload.get("chatId");
            String messageId = (String) payload.get("messageId");

            chatService.processUserMessage(chatId, messageId, message);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to process user message.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("chatId", System.currentTimeMillis()));

    }
}
