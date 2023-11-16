package com.dreamteam.powerofwar.server.game.event;

import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.server.game.GameProgram;
import com.dreamteam.powerofwar.server.game.exception.TooLessResourcesException;
import com.dreamteam.powerofwar.server.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.server.game.player.Player;

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
        } catch (TooManyObjectsException | TooLessResourcesException ignore) {
            // TODO: we have to notify the owner somehow
        }
    }
}
