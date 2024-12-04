package com.example.communicationapp.communication;

import com.example.communicationapp.model.IPlayer;
import com.example.communicationapp.model.Message;

/**
 * The Mediator interface defines a contract for mediating communication between
 * objects, typically in a loosely coupled system. Classes implementing this interface
 * act as intermediaries, enabling objects to interact without having direct references
 * to each other.
 *
 * This interface is commonly used in the Mediator design pattern to centralize
 * and coordinate communication between multiple components or entities.
 */
public interface Mediator {
  void sendMessage(Message message, IPlayer sender);
}
