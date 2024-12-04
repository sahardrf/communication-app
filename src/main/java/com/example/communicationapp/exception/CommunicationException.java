package com.example.communicationapp.exception;

/**
 * The CommunicationException class represents exceptions that can occur during
 * the communication between players.
 */
public class CommunicationException extends RuntimeException {
    public CommunicationException(String message) {
        super(message);
    }
}