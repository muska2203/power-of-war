package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.player.Player;

/**
 * Событие добавление игрового объекта.
 */
public class AddGameObjectEvent extends BaseEvent {

    private Player owner;
    private GameObjectType type;

    public AddGameObjectEvent(double x, double y, Player owner, GameObjectType type) {
        super(x, y);
        this.owner = owner;
        this.type = type;
    }

    @Override
    public void execute(GameProgram gameProgram) {
        try {
            gameProgram.addGameObject(owner.getObjectFactory().createObject(x, y, type));
        } catch (TooManyObjectsException ignore) {}
    }
}
