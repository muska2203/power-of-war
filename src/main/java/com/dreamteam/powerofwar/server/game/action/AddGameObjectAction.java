package com.dreamteam.powerofwar.server.game.action;

import com.dreamteam.powerofwar.server.game.Board;
import com.dreamteam.powerofwar.server.game.exception.TooLessResourcesException;
import com.dreamteam.powerofwar.server.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.server.game.player.Player;

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
        } catch (TooManyObjectsException | TooLessResourcesException ignore) {
            // TODO: we have to notify the owner somehow
        }
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
