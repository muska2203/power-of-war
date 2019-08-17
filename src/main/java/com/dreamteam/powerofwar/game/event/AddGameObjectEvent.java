package com.dreamteam.powerofwar.game.event;

import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.object.GameObjectCreator;

/**
 * Событие добавление игрового объекта.
 */
public class AddGameObjectEvent extends BaseEvent {

    /**
     * Создатель игровых объектов.
     */
    private GameObjectCreator creator;

    public AddGameObjectEvent(double x, double y, GameObjectCreator creator) {
        super(x, y);
        this.creator = creator;
    }

    @Override
    public void execute(GameProgram gameProgram) {
        gameProgram.addGameObject(creator.create(x, y));
    }
}
