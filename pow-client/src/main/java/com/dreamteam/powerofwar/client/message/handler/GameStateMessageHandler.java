package com.dreamteam.powerofwar.client.message.handler;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.game.GameContext;
import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.message.types.FullGameStateMessage;
import com.dreamteam.powerofwar.connection.message.types.GameObjectInfo;

@Component
public class GameStateMessageHandler implements MessageHandler<FullGameStateMessage> {

    private final GameContext gameContext;

    public GameStateMessageHandler(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void handle(FullGameStateMessage message) {
        gameContext.setResources(message.getResources());
        gameContext.getGameObjects().clear();
        Map<Integer, GameObjectInfo> mappedObjects = message.getGameObjectsInfo()
                .stream()
                .collect(Collectors.toMap(GameObjectInfo::getId, Function.identity()));
        gameContext.getGameObjects().putAll(mappedObjects);
    }
}
