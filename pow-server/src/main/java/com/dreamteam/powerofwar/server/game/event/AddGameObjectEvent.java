package com.dreamteam.powerofwar.server.game.event;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.server.game.GameProgram;
import com.dreamteam.powerofwar.server.game.exception.TooLessResourcesException;
import com.dreamteam.powerofwar.server.game.exception.TooManyObjectsException;

/**
 * Событие добавление игрового объекта.
 */
public class AddGameObjectEvent extends BaseEvent {

    private final Player owner;
    private final GameObjectType type;

    public AddGameObjectEvent(double x, double y, Player owner, GameObjectType type) {
        super(x, y);
        this.owner = owner;
        this.type = type;
    }

    @Override
    public void execute(GameProgram gameProgram) {
        try {
            gameProgram.addGameObject(gameProgram.createObject(x, y, type, owner));
        } catch (TooManyObjectsException | TooLessResourcesException ignore) {
            // TODO: we have to notify the owner somehow
        }
    }
}
