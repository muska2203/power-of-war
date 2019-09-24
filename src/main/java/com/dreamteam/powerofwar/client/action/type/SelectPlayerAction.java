package com.dreamteam.powerofwar.client.action.type;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.game.player.Player;

public class SelectPlayerAction implements Action {

    private Player player;

    public SelectPlayerAction(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
