package com.example.communicationapp.communication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.communicationapp.exception.CommunicationException;
import com.example.communicationapp.model.IPlayer;
import com.example.communicationapp.model.Message;

class CommunicationChannelTest {

    private CommunicationChannel channel;
    private IPlayer player1;
    private IPlayer player2;
    private Message message;

    @BeforeEach
    void setUp() {
        channel = new CommunicationChannel();
        player1 = mock(IPlayer.class);
        player2 = mock(IPlayer.class);
        message = new Message("Test Message", 1);
        List<IPlayer> players = Arrays.asList(player1, player2);
        channel.setPlayers(players);
    }

    @Test
    void testSendMessage() {
        channel.sendMessage(message, player1);

        verify(player2, times(1)).receiveMessage(message);
        verify(player1, never()).receiveMessage(message);
    }

    @Test
    void testSendMessageNoPlayers() {
        channel.setPlayers(Collections.emptyList());

        assertThrows(CommunicationException.class, () -> channel.sendMessage(message, player1));
    }

    @Test
    void testSendMessageSinglePlayer() {
        channel.setPlayers(Arrays.asList(player1));
        channel.sendMessage(message, player1);

        verify(player1, never()).receiveMessage(any());
    }

}
