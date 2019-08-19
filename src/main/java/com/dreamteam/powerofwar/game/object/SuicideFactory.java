package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.AddGameObjectAction;
import com.dreamteam.powerofwar.game.user.User;

import static com.dreamteam.powerofwar.phisics.Units.*;

public class SuicideFactory extends BaseGameObject {

    private static final long reloadingTime = 4;
    private double timeAfterLastAction = 0;

    SuicideFactory(double x, double y, User user) {
        super(x, y, BUILDING_SIZE, BUILDING_DEFAULT_VISIBILITY_RADIUS, BUILDING_DEFAULT_ACTION_RADIUS, GameObjectType.SUICIDE_FACTORY, user);
    }

    @Override
    public void update(Board board) {
        if (timeAfterLastAction > reloadingTime) {
            timeAfterLastAction -= reloadingTime;
            board.addAction(new AddGameObjectAction(board, getX(), getY(), GameObjectType.SUICIDE, this.getOwner()));
        }
    }

    @Override
    public void move(long loopTime) {
        timeAfterLastAction += loopTime * SPEED;
        //do nothing
    }
}
