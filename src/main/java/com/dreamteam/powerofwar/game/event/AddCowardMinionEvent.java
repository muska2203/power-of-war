package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.object.CowardMinion;

public class AddCowardMinionEvent extends BaseEvent {

    public AddCowardMinionEvent(double x, double y) {
        super(x, y);
    }

    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.addGameObject(new CowardMinion(this.x, this.y));
    }
}
