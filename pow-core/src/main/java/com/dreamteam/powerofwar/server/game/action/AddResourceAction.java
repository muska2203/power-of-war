package com.dreamteam.powerofwar.server.game.action;

import com.dreamteam.powerofwar.game.types.ResourceType;
import com.dreamteam.powerofwar.server.game.player.Player;

public class AddResourceAction implements Action {

    private ResourceType resource;
    private Integer count;
    private Player player;

    public AddResourceAction(ResourceType resource, Integer count, Player player) {
        this.resource = resource;
        this.count = count;
        this.player = player;
    }

    @Override
    public void execute() {
        player.getContext().addResource(resource, count);
    }

    @Override
    public boolean isReady(long gameLoop) {
        return true;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
