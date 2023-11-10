package com.dreamteam.powerofwar.server.game.event;

import com.dreamteam.powerofwar.server.game.GameProgram;

public class StopGameEvent implements Event {
    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.stopGame();
    }
}
