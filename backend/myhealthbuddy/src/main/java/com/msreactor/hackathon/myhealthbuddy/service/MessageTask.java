/*
 * Copyright (c) 2025 Nextiva, Inc. to Present.
 * All rights reserved.
 */

package com.msreactor.hackathon.myhealthbuddy.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.msreactor.hackathon.myhealthbuddy.model.WebSocketPayload;


public class MessageTask implements Runnable{
    private SimpMessagingTemplate messagingTemplate;

    private WebSocketPayload webSocketPayload;

    private static final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public MessageTask(SimpMessagingTemplate messagingTemplate, WebSocketPayload webSocketPayload){
        this.messagingTemplate = messagingTemplate;
        this.webSocketPayload = webSocketPayload;
    }

    @Override public void run() {

        ReentrantLock lock = null;

        try {

            String chatId = webSocketPayload.getChatId();

            lock = locks.computeIfAbsent(chatId, id -> new ReentrantLock());
            lock.lock();

            System.out.println("Processing message for chatId: " + chatId);

            messagingTemplate.convertAndSend(String.format("/topic/chat-%s",chatId), webSocketPayload);

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        } catch (Exception ex){
            System.out.println("Exception in MessageTask: " + ex.getMessage());
        } finally {
            if (lock != null) {
                lock.unlock();
            }
            locks.remove(webSocketPayload.getChatId(), lock);
        }

    }
}
