package com.example.communicationapp;

import com.example.communicationapp.communication.CommunicationChannel;
import com.example.communicationapp.model.IPlayer;
import com.example.communicationapp.model.Message;
import com.example.communicationapp.model.Player;
import com.example.communicationapp.service.MessageService;
import java.util.Arrays;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The CommunicationApp class is the entry point for simulating a communication system
 * using the Mediator design pattern. It demonstrates two scenarios:
 * <ol>
 *   <li>Single-threaded communication between players.</li>
 *   <li>Multi-threaded communication using a thread pool.</li>
 * </ol>
 * <p>
 * This application creates {@link Player}, {@link CommunicationChannel}, and {@link MessageService}
 * instances to simulate message exchange. It also uses {@link ExecutorService} to manage
 * threads in the multi-threaded scenario.
 */
public class CommunicationApp {
  public static void main(String[] args) {
    MessageService messageService = new MessageService();

    // Scenario 1: Single-threaded communication
    System.out.println("Scenario 1: Single-threaded communication");
    CommunicationChannel channel1 = new CommunicationChannel();
    IPlayer player1 = new Player("Player1", channel1, messageService);
    IPlayer player2 = new Player("Player2", channel1, messageService);
    channel1.setPlayers(Arrays.asList(player1, player2));
    ((Player) player1).setInitiator(true);
    Message initialMessage = new Message("Hello! ", 0);
    player1.sendMessage(initialMessage);

    // Print statistics after scenario 1
    ((Player) player1).printMessageStatistics();
    ((Player) player2).printMessageStatistics();

    // Scenario 2: Multi-threaded communication
    System.out.println("\n\nScenario 2: Multi-threaded communication");
    CommunicationChannel channel2 = new CommunicationChannel();
    IPlayer player3 = new Player("Player1", channel2, messageService);
    IPlayer player4 = new Player("Player2", channel2, messageService);
    channel2.setPlayers(Arrays.asList(player3, player4));
    ((Player) player3).setInitiator(true);
    ((Player) player3).setInitialMessage(new Message("Hello from thread! ", 0));

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

    completionService.submit(
        () -> {
          ((Player) player3).run();
          return ((Player) player3).getName() + " has finished execution.";
        });

    completionService.submit(
        () -> {
          ((Player) player4).run();
          return ((Player) player4).getName() + " has finished execution.";
        });
    executorService.shutdown();
    try {
      for (int i = 0; i < 2; i++) { // Wait for both tasks to complete
        Future<String> result = completionService.take(); // Blocks until one thread finishes
        System.out.println(result.get());
      }
    } catch (Exception e) {
      System.err.println("Error while processing threads: " + e.getMessage());
    }
  }
}
