package com.dreamteam.powerofwar.server.game.action;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.server.game.exception.TooLessResourcesException;
import com.dreamteam.powerofwar.server.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.server.game.object.Board;

/**
 * Действие добавления игрового объекта в игру.
 */
public class AddGameObjectAction implements Action {

    private final Board board;
    private final double x;
    private final double y;
    private final Player owner;
    private final GameObjectType type;

    /**
     * Запоминает контекст, в который требуется добавить игровой объект.
     * Само добавленгие производится после вызова основного метода {@link Action#execute()}.
     *
     * @param board игровое поле, на которое требуется добавить игровой объект.
     * @param x     позиция по оси OX.
     * @param y     позиция по оси OX.
     * @param owner игрок, для которого требуется создать игровой объект.
     * @param type  тип игрового объекта.
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
            board.addGameObject(board.createObject(x, y, type, owner));
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
