package com.dreamteam.powerofwar.client.state.subject;

import com.dreamteam.powerofwar.game.player.Player;

public class SelectedPlayer {

    final private Player player;

    public SelectedPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
