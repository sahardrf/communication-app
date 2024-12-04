package com.example.communicationapp.communication;

import com.example.communicationapp.exception.CommunicationException;
import com.example.communicationapp.model.IPlayer;
import com.example.communicationapp.model.Message;
import java.util.List;

/**
 * The CommunicationChannel class acts as a mediator for facilitating communication
 * between players in a system. It ensures that messages sent by one player are delivered
 * to all other players in the channel, except the sender. The class implements the
 * Mediator design pattern.
 */

public class CommunicationChannel implements Mediator {
  private List<IPlayer> players;

  public void setPlayers(List<IPlayer> players) {
    this.players = players;
  }

  @Override
  public void sendMessage(Message message, IPlayer sender) {
    if (players == null || players.isEmpty()) {
      throw new CommunicationException("No players available to receive the message.");
    }

    for (IPlayer player : players) {
      if (player != sender) {
        player.receiveMessage(message);
      }
    }
  }
}
