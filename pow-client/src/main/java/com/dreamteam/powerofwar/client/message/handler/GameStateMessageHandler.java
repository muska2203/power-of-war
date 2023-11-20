package com.dreamteam.powerofwar.client.message.handler;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.game.GameContext;
import com.dreamteam.powerofwar.client.message.GameStateMessage;
import com.dreamteam.powerofwar.connection.MessageHandler;

@Component
public class GameStateMessageHandler implements MessageHandler<GameStateMessage> {

    private final GameContext gameContext;

    public GameStateMessageHandler(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void handle(GameStateMessage message) {
        gameContext.setResources(message.getResources());
        gameContext.getGameObjects().clear();
        gameContext.getGameObjects().putAll(message.getGameObjects());
    }
}
