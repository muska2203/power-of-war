package com.dreamteam.powerofwar.client.message.handler;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.message.GameStateMessage;
import com.dreamteam.powerofwar.client.state.State;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.connection.MessageHandler;

@Component
public class GameStateMessageHandler implements MessageHandler<GameStateMessage> {

    private State<SelectedPlayer> selectedPlayerState;

    public GameStateMessageHandler(State<SelectedPlayer> selectedPlayerState) {
        this.selectedPlayerState = selectedPlayerState;
    }

    @Override
    public void handle(GameStateMessage message) {
        SelectedPlayer selectedPlayer = selectedPlayerState.get();
        selectedPlayer.getPlayer().getContext().setResources(message.getResources());
    }
}
