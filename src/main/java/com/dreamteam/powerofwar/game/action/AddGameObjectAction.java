package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.GameObjectCreator;
import com.dreamteam.powerofwar.game.player.Player;

/**
 * Действие добавления игрового объекта в игру.
 */
public class AddGameObjectAction implements Action {

    private Board board;
    private GameObjectCreator creator;
    private double x;
    private double y;
    private Player owner;

    /**
     * Запоминает контекст, в который требуется добавить игровой объект.
     * Само добавленгие производится после вызова основного метода {@link Action#execute()}.
     *
     * @param board игровое поле, на которое требуется добавить игровой объект.
     * @param x позиция по оси OX.
     * @param y позиция по оси OX.
     * @param creator производитель игрового объекта.
     * @param owner игрок, для которого требуется создать игровой объект.
     */
    public AddGameObjectAction(Board board, double x, double y, GameObjectCreator creator, Player owner) {
        this.board = board;
        this.creator = creator;
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    @Override
    public void execute() {
        try {
            board.addGameObject(creator.create(x, y, owner));
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
