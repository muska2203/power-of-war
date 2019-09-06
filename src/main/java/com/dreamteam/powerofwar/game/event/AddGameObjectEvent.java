package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.GameObjectCreator;
import com.dreamteam.powerofwar.game.player.Player;

/**
 * Событие добавление игрового объекта.
 */
public class AddGameObjectEvent extends BaseEvent {

    /**
     * Создатель игровых объектов.
     */
    private GameObjectCreator creator;
    private Player owner;

    public AddGameObjectEvent(double x, double y, GameObjectCreator creator, Player owner) {
        super(x, y);
        this.creator = creator;
        this.owner = owner;
    }

    @Override
    public void execute(GameProgram gameProgram) {
        try {
            gameProgram.addGameObject(creator.create(x, y, owner));
        } catch (TooManyObjectsException ignore) {}
    }
}
