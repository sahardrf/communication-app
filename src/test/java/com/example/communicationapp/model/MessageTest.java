package com.example.communicationapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageCreation() {
        String content = "Hello, World!";
        int counter = 5;

        Message message = new Message(content, counter);

        assertEquals(content, message.getContent());
        assertEquals(counter, message.getCounter());
    }

    @Test
    public void testSettersAndGetters() {
        Message message = new Message("Initial Content", 1);

        message.setContent("Updated Content");
        message.setCounter(10);

        assertEquals("Updated Content", message.getContent());
        assertEquals(10, message.getCounter());
    }
}
