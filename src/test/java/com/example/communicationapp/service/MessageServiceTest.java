package com.example.communicationapp.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.communicationapp.model.Message;
import org.junit.jupiter.api.Test;

public class MessageServiceTest {

  @Test
  void testCreateReply() {
    MessageService messageService = new MessageService();
    Message originalMessage = new Message("Hello!", 0);

    Message replyMessage = messageService.createReply(originalMessage);

    assertNotNull(replyMessage, "Reply message should not be null.");
    assertEquals(
        "Hello! 0",
        replyMessage.getContent(),
        "Reply content should append the counter of the original message.");
    assertEquals(1, replyMessage.getCounter(), "Reply counter should increment by 1.");
  }

  @Test
  void testCreateReplyWithNonEmptyCounter() {
    MessageService messageService = new MessageService();
    Message originalMessage = new Message("Test", 5);

    Message replyMessage = messageService.createReply(originalMessage);

    assertNotNull(replyMessage, "Reply message should not be null.");
    assertEquals(
        "Test 5",
        replyMessage.getContent(),
        "Reply content should append the current counter of the original message.");
    assertEquals(6, replyMessage.getCounter(), "Reply counter should increment by 1.");
  }
}
