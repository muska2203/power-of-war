package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.player.Player;

/**
 * Действие добавления игрового объекта в игру.
 */
public class AddGameObjectAction implements Action {

    private Board board;
    private double x;
    private double y;
    private Player owner;
    private GameObjectType type;
    /**
     * Запоминает контекст, в который требуется добавить игровой объект.
     * Само добавленгие производится после вызова основного метода {@link Action#execute()}.
     *
     * @param board игровое поле, на которое требуется добавить игровой объект.
     * @param x позиция по оси OX.
     * @param y позиция по оси OX.
     * @param owner игрок, для которого требуется создать игровой объект.
     * @param type тип игрового объекта.
     */
    public AddGameObjectAction(Board board, double x, double y, Player owner, GameObjectType type) {
        this.board = board;
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.type = type;
    }

    @Override
    public void execute() {
        try {
            board.addGameObject(owner.getObjectFactory().createObject(x, y, type));
        } catch (TooManyObjectsException ignore) {}
    }

    @Override
    public boolean isReady(long gameLoop) {
        return true;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
