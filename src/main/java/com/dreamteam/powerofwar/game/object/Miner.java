package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.StoreResourcesAction;
import com.dreamteam.powerofwar.game.action.MineResourceAction;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: JavaDoc
 */
public abstract class Miner extends BaseGameObject {

    protected int value = 0;
    protected GameObject target;
    private MinerState state;

    Miner(double x, double y, Player player) {
        super(x, y, new Vector(), player);
        this.state = MinerState.NONE;
    }

    public int getValue() {
        return value;
    }

    public void addResource(int value) {
        this.value += value;
    }

    @Override
    public String toString() {
        return "Miner[ " + this.getValue() + " / " + this.getCapacity() + " ]";
    }

    public void resetValue() {
        this.value = 0;
    }

    @Override
    public void update(Board board) {
        System.out.println(this.state);
        switch (this.state) {
            case NONE: {
                // target == null
                this.target = getNearestResource(board);
                this.state = MinerState.SEARCH;
                // Если ресурс не найден или майнер уже не может больше набрать ресурса, то идем на базу.
                if (this.target == null) {
                    this.target = getNearestBase(board);
                    if (this.target != null) {
                        this.state = MinerState.DELIVERY;
                    }
                }
                this.setSpeedVector(this.target == null ? new Vector() : Vector.byTarget(this, target));
                break;
            }
            case SEARCH: {
                // target == Resource
                if (this.target == null || this.target.isDead()) {
                    this.target = null;
                    this.state = MinerState.NONE;
                    this.setSpeedVector(new Vector());
                } else {
                    if (GameObjectUtils.checkPossibilityAction(this, target)) {
                        this.state = MinerState.MINING;
                        this.setSpeedVector(new Vector());
                    }
                }
                break;
            }
            case MINING: {
                // target == Resource
                if (this.target == null || this.target.isDead()) {
                    this.state = MinerState.NONE;
                    this.target = null;
                    this.setSpeedVector(new Vector());
                } else {
                    if (this.isFull()) {
                        this.target = this.getNearestBase(board);
                        this.state = this.target == null ? MinerState.NONE : MinerState.DELIVERY;
                        this.setSpeedVector(this.target == null ? new Vector() : Vector.byTarget(this, this.target));
                    } else {
                        board.addAction(new MineResourceAction((Resource) this.target, this));
                    }
                }
                break;
            }
            case DELIVERY: {
                // target == Base
                // На каждом ходу обновляем местонахождение базы.
                // Вдруг уже построен еще один "склад", тогда меняем цель для движения.
                // TODO: проверить необходимость этого обновления.
                this.target = getNearestBase(board);
                if (GameObjectUtils.checkPossibilityAction(this, this.target)) {
                    board.addAction(new StoreResourcesAction(this));
                    this.state = MinerState.NONE;
                    this.target = null;
                    this.setSpeedVector(new Vector());
                }
                break;
            }
        }
    }

    private GameObject getNearestResource(Board board) {
        return GameObjectUtils.getNearestObject(this, board.getGameObjects(),
                gameObject -> getResourceType().equals(gameObject.getType()));
    }

    private GameObject getNearestBase(Board board) {
        return GameObjectUtils.getNearestObject(this, board.getGameObjects(),
                gameObject -> BuildingType.BASE.equals(gameObject.getType()),
                gameObject -> !this.getOwner().isEnemyFor(gameObject.getOwner()));
    }

    public abstract ResourceType getResourceType();

    public abstract int getCapacity();

    public boolean isFull() {
        return value >= getCapacity();
    }

    private enum MinerState {
        /**
         * Бездействует
         */
        NONE,

        /**
         * Поиск ресурса/Движение к ресурсу
         */
        SEARCH,

        /**
         * Добыча ресурса
         */
        MINING,

        /**
         * Доставка ресурса на базу
         */
        DELIVERY
    }
}
