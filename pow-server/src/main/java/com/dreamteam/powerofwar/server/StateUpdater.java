package com.dreamteam.powerofwar.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.ResourceType;
import com.dreamteam.powerofwar.server.game.object.Board;
import com.dreamteam.powerofwar.server.message.GameStateMessage;

@Component
public class StateUpdater {

    private final ServerConnection serverConnection;
    private final Board board;

    public StateUpdater(ServerConnection serverConnection, Board board) {
        this.serverConnection = serverConnection;
        this.board = board;
    }

    public void start() {
        Map<ResourceType, Integer> resources = new HashMap<>();
        Thread thread = new Thread(() -> {
            while (true) {
                for (Player player : board.getPlayers()) {
                    resources.put(ResourceType.GOLD, player.getContext().getResource(ResourceType.GOLD));
                    List<GameStateMessage.GameObjectInfo> gameObjectsInfo = board.getGameObjects()
                            .stream()
                            .map(gameObject -> new GameStateMessage.GameObjectInfo(gameObject,
                                    gameObject.getOwner() != player))
                            .collect(Collectors.toList());
                    serverConnection.sendMessage(new GameStateMessage(resources, gameObjectsInfo), player);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
