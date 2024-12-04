package com.example.communicationapp.model;

/**
 * The IPlayer interface defines the contract for any entity that participates
 * in a communication system. Implementing classes represent players that can
 * send and receive messages, as well as terminate communication when needed.
 *
 */
public interface IPlayer {
    void sendMessage(Message message);
    void receiveMessage(Message message);
    void stopCommunication(String reason);
}