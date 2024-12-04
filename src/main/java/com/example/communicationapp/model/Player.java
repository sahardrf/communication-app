package com.example.communicationapp.model;

import com.example.communicationapp.communication.Mediator;
import com.example.communicationapp.service.MessageService;
/**
 * The Player class represents a participant in a communication system.
 * It implements the {@link IPlayer} interface and serves as a thread
 * capable of sending and receiving messages through a {@link Mediator}.
 * 
 * Each Player maintains message statistics, enforces message limits,
 * and ensures thread safety for critical operations using synchronization.
 */

public class Player implements Runnable, IPlayer {
    private  String name;
    private boolean isInitiator;
    private  Mediator mediator;
    private  MessageService messageService;
    private int messageCount = 0;
    private final int MAX_MESSAGES = 19;
    private boolean running = false; // boolean is not thread safe, lock is used to guarantee the thread safety
    private final Object lock = new Object(); // Lock for thread safety
    private Message initialMessage;

    private int sentMessagesCount = 0; // Counter for sent messages
    private int receivedMessagesCount = 0; // Counter for received messages

    public Player(String name, Mediator mediator, MessageService messageService) {
        this.name = name;
        this.mediator = mediator;
        this.messageService = messageService;
    }

    @Override
    public void run() {
        setRunning(true);
        if (isInitiator && initialMessage != null) {
            sendMessage(initialMessage);
        }
        printMessageStatistics();
    }

    @Override
    public void sendMessage(Message message) {
        synchronized (lock) {
        if (messageCount < MAX_MESSAGES) {
            System.out.println(name + " sending: " + message.getContent());
            mediator.sendMessage(message, this);
            messageCount++;
            sentMessagesCount++;
        } else {
            stopCommunication("Maximum message limit reached.");
        }
    }
    }

    @Override
    public void receiveMessage(Message message) {
        synchronized (lock) {
        System.out.println(name + " received: " + message.getContent());
        receivedMessagesCount++;
        if (message.getCounter() < MAX_MESSAGES) {
            Message replyMessage = messageService.createReply(message);
            sendMessage(replyMessage);
        } else {
            stopCommunication("Maximum message limit reached.");
        }
    }
    }

    @Override
    public void stopCommunication(String reason) {
        System.out.println(reason);
        setRunning(false);
    }

    public void printMessageStatistics() {
        System.out.println(getName() + " sent " + sentMessagesCount + " messages.");
        System.out.println(name + " received " + receivedMessagesCount + " messages.");
    }

    // Thread-safe setter for running
    protected void setRunning(boolean value) {
        synchronized (lock) {
            running = value;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isInitiator() {
        return isInitiator;
    }

    public Mediator getMediator() {
        return mediator;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public boolean isRunning() {
        return running;
    }

    public Object getLock() {
        return lock;
    }

    public Message getInitialMessage() {
        return initialMessage;
    }

    public int getSentMessagesCount() {
        return sentMessagesCount;
    }

    public int getReceivedMessagesCount() {
        return receivedMessagesCount;
    }

    public void setInitiator(boolean isInitiator) {
        this.isInitiator = isInitiator;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public void setInitialMessage(Message initialMessage) {
        this.initialMessage = initialMessage;
    }

    public void setSentMessagesCount(int sentMessagesCount) {
        this.sentMessagesCount = sentMessagesCount;
    }

    public void setReceivedMessagesCount(int receivedMessagesCount) {
        this.receivedMessagesCount = receivedMessagesCount;
    }
}