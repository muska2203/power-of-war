package com.dreamteam.powerofwar.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.ResourceType;
import com.dreamteam.powerofwar.connection.message.types.FullGameStateMessage;
import com.dreamteam.powerofwar.connection.message.types.GameObjectInfo;
import com.dreamteam.powerofwar.server.game.object.Board;

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
                    List<GameObjectInfo> gameObjectsInfo = board.getGameObjects()
                            .stream()
                            .map(gameObject -> new GameObjectInfo(
                                    gameObject.getId(),
                                    gameObject.getX(),
                                    gameObject.getY(),
                                    gameObject.getHealth(),
                                    gameObject.getType(),
                                    gameObject.getOwner() != player,
                                    gameObject.getSize()
                            ))
                            .collect(Collectors.toList());
                    serverConnection.sendMessage(new FullGameStateMessage(resources, gameObjectsInfo), player);
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
