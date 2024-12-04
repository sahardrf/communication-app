package com.example.communicationapp.service;

import com.example.communicationapp.model.Message;

/**
 * The MessageService class provides utility methods for creating and processing messages.
 * It is typically used in a communication system to generate reply messages based on
 * original messages.
 */
public class MessageService {
    public Message createReply(Message originalMessage) {
        String newContent = originalMessage.getContent() + " " + originalMessage.getCounter();
        return new Message(newContent, originalMessage.getCounter() + 1);
    }
}
