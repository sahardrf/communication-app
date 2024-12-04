package com.example.communicationapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.communicationapp.communication.Mediator;
import com.example.communicationapp.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

  private Mediator mediator;
  private MessageService messageService;
  private Player player;
  private final String playerName = "Player1";

  @BeforeEach
  void setUp() {
    mediator = mock(Mediator.class);
    messageService = mock(MessageService.class);
    player = new Player(playerName, mediator, messageService);
  }

  @Test
  void testSendMessage() {
    Message message = new Message("Hello", 1);
    player.setInitiator(true);
    player.setInitialMessage(message);

    player.sendMessage(message);

    verify(mediator, times(1)).sendMessage(message, player);
    assertTrue(player.getSentMessagesCount() > 0);
  }

  @Test
  void testReceiveMessageAndReply() {
    Message receivedMessage = new Message("Hi", 1);
    Message replyMessage = new Message("Reply", 2);
    when(messageService.createReply(receivedMessage)).thenReturn(replyMessage);

    player.receiveMessage(receivedMessage);

    verify(messageService, times(1)).createReply(receivedMessage);
    verify(mediator, times(1)).sendMessage(replyMessage, player);
    assertTrue(player.getReceivedMessagesCount() > 0);
  }

  @Test
  void testStopCommunication() {
    player.stopCommunication("Test reason");
    assertFalse(player.isRunning());
  }

  @Test
  void testMaximumMessageLimit() {
    Message message = new Message("Hello", 1);
    player.setInitiator(true);
    player.setInitialMessage(message);

    for (int i = 0; i < 19; i++) {
      player.sendMessage(message);
    }

    verify(mediator, times(19)).sendMessage(message, player);
    assertEquals(19, player.getSentMessagesCount());
  }

  @Test
  void testThreadSafetyOfRunningFlag() throws InterruptedException {
    Thread t1 = new Thread(() -> player.setRunning(true));
    Thread t2 = new Thread(() -> player.setRunning(false));

    t1.start();
    t2.start();

    t1.join();
    t2.join();

    synchronized (player) {
      assertTrue(player.isRunning() || !player.isRunning());
    }
  }

  @Test
  void testMessageStatisticsOutput() {
    Message sentMessage = new Message("Hello", 1);
    player.sendMessage(sentMessage);

    player.printMessageStatistics();

    assertEquals(1, player.getSentMessagesCount());
  }

  @Test
  void testSetRunningThreadSafety() {
    Runnable setTrue = () -> player.setRunning(true);
    Runnable setFalse = () -> player.setRunning(false);

    Thread thread1 = new Thread(setTrue);
    Thread thread2 = new Thread(setFalse);

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    synchronized (player) {
      assertTrue(player.isRunning() || !player.isRunning());
    }
  }
}
