package com.dreamteam.powerofwar.server.game.action;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.ResourceType;

public class AddResourceAction implements Action {

    private final ResourceType resource;
    private final Integer count;
    private final Player player;

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
