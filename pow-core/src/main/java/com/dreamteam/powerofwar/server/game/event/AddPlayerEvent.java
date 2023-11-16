package com.dreamteam.powerofwar.server.game.event;

import com.dreamteam.powerofwar.server.game.GameProgram;
import com.dreamteam.powerofwar.server.game.player.Player;

public class AddPlayerEvent implements Event {

    private Player player;

    public AddPlayerEvent(Player player) {
        this.player = player;
    }

    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.addPlayer(player);
    }
}
