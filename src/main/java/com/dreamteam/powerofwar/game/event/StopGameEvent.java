package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;

public class StopGameEvent implements Event {
    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.stopGame();
    }
}
