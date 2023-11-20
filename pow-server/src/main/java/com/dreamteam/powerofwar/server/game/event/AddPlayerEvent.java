package com.dreamteam.powerofwar.server.game.event;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.server.game.GameProgram;

public class AddPlayerEvent implements Event {

    private final Player player;

    public AddPlayerEvent(Player player) {
        this.player = player;
    }

    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.addPlayer(player);
    }
}
