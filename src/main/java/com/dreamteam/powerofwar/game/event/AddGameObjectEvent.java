package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.object.GameObjectCreator;
import com.dreamteam.powerofwar.game.user.User;

/**
 * Событие добавление игрового объекта.
 */
public class AddGameObjectEvent extends BaseEvent {

    /**
     * Создатель игровых объектов.
     */
    private GameObjectCreator creator;
    private User owner;

    public AddGameObjectEvent(double x, double y, GameObjectCreator creator, User owner) {
        super(x, y);
        this.creator = creator;
        this.owner = owner;
    }

    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.addGameObject(creator.create(x, y, owner));
    }
}
