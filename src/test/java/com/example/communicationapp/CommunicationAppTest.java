package com.example.communicationapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.communicationapp.communication.CommunicationChannel;
import com.example.communicationapp.model.IPlayer;
import com.example.communicationapp.model.Message;
import com.example.communicationapp.model.Player;
import com.example.communicationapp.service.MessageService;

public class CommunicationAppTest {

  private CommunicationChannel channel;
  private MessageService messageService;
  private IPlayer player1;
  private IPlayer player2;

  @BeforeEach
  void setUp() {
    channel = new CommunicationChannel();
    messageService = new MessageService();
    player1 = new Player("Player1", channel, messageService);
    player2 = new Player("Player2", channel, messageService);
    channel.setPlayers(Arrays.asList(player1, player2));
  }

  @Test
  void testSingleThreadedCommunication() {
    ((Player) player1).setInitiator(true);
    Message initialMessage = new Message("Hello!", 0);

    player1.sendMessage(initialMessage);

    assertEquals(
        10,
        ((Player) player2).getReceivedMessagesCount(),
        "Player2 should have received 10 message.");

    assertEquals(
        10, ((Player) player1).getSentMessagesCount(), "Player1 should have sent 10 message.");
  }

  @Test
  void testMultiThreadedCommunication() throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    ((Player) player1).setInitiator(true);
    ((Player) player1).setInitialMessage(new Message("Hello from thread!", 0));

    executorService.submit((Runnable) player1);
    executorService.submit((Runnable) player2);

    ((Player) player1).run();

    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.SECONDS);
    assertTrue(((Player) player1).getSentMessagesCount() > 0, "Player1 should have sent messages.");
    assertTrue(
        ((Player) player2).getReceivedMessagesCount() > 0,
        "Player2 should have received messages.");
  }

  @Test
  void testCommunicationExceptionHandling() {
    ((Player) player1).setInitiator(true);
    for (int i = 0; i < 30; i++) {
      Message message = new Message("Message " + i, i);
      try {
        player1.sendMessage(message);
      } catch (RuntimeException e) {
        assertTrue(
            e instanceof com.example.communicationapp.exception.CommunicationException,
            "Exception should be of type CommunicationException.");
        break;
      }
    }
  }

  @Test
  void testMessageServiceReplyGeneration() {
    Message originalMessage = new Message("Hello!", 0);
    Message replyMessage = messageService.createReply(originalMessage);

    assertEquals("Hello! 0", replyMessage.getContent(), "Reply content should append the counter.");
    assertEquals(1, replyMessage.getCounter(), "Reply counter should increment by 1.");
  }

  @Test
  void testEndToEndCommunication() {
    ((Player) player1).setInitiator(true);
    ((Player) player1).setInitialMessage(new Message("End-to-End Test", 0));
    ((Player) player1).run();
    ((Player) player2).run();

    assertEquals(10, ((Player) player1).getSentMessagesCount());
    assertEquals(10, ((Player) player2).getReceivedMessagesCount());
  }
}
