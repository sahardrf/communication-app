package com.example.communicationapp.model;


/**
 * The Message class represents a communication message that can be
 * sent and received in a system. It encapsulates the content of the
 * message and a counter to track its sequence or occurrence.
 *
 * This class is typically used in systems implementing the Mediator design
 * pattern to facilitate communication between participants.
 */

public class Message {
  private String content;
  private int counter;
  
  public Message(String content, int counter) {
    this.content = content;
    this.counter = counter;
  }
  public String getContent() {
    return content;
  }
  public int getCounter() {
    return counter;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public void setCounter(int counter) {
    this.counter = counter;
  }
}