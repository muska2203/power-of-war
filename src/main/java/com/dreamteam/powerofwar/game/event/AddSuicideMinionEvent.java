package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.object.SuicideObject;

public class AddSuicideMinionEvent extends BaseEvent {

    public AddSuicideMinionEvent(double x, double y) {
        super(x, y);
    }

    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.addGameObject(new SuicideObject(this.x, this.y));
    }
}