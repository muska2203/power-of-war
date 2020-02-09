package com.dreamteam.powerofwar.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.server.message.GameStateMessage;

@Component
public class StateUpdater {

    private ServerConnection serverConnection;
    private Board board;

    public StateUpdater(ServerConnection serverConnection, Board board) {
        this.serverConnection = serverConnection;
        this.board = board;
    }

    public void start() {
        Map<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>(){{
            put(ResourceType.GOLD, 1);
        }};
        Thread thread = new Thread(() -> {
            while (true) {
                resources.put(ResourceType.GOLD, resources.get(ResourceType.GOLD) + 1);
                serverConnection.sendMessage(new GameStateMessage(resources));
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
