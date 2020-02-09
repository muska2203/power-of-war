package com.dreamteam.powerofwar.client.ui.playground;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Box;
import javax.swing.JPanel;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.action.type.SelectGameObjectAction;
import com.dreamteam.powerofwar.client.action.type.SelectPlayerAction;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.GameObject;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.handler.Dispatcher;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ControlComponent extends JPanel {

    private Dispatcher<Action> actionDispatcher;
    private Board board;

    public ControlComponent(Dispatcher<Action> actionDispatcher, Board board, Player firstPlayer, Player secondPlayer) {
        this.actionDispatcher = actionDispatcher;
        this.board = board;

        Box box = Box.createHorizontalBox();
        Button warrior = new Button("Warrior");
        Button cowardMinion = new Button("Coward Minion");
        Button resetChoice = new Button("Reset choice");
        Button cleanField = new Button("Clean field");
        Button firstUser = new Button("First Player");
        Button secondUser = new Button("Second Player");
        Button goldMiner = new Button("Gold Miner");
        Button gold = new Button("Gold");
        Button base = new Button("Base");
        warrior.addActionListener(selectGameObject(UnitType.WARRIOR));
        cowardMinion.addActionListener(selectGameObject(UnitType.COWARD));
        resetChoice.addActionListener(reset());
        cleanField.addActionListener(e -> this.killAllObject());
        firstUser.addActionListener(selectPlayer(firstPlayer));
        secondUser.addActionListener(selectPlayer(secondPlayer));
        goldMiner.addActionListener(selectGameObject(UnitType.GOLD_MINER));
        gold.addActionListener(selectGameObject(ResourceType.GOLD));
        base.addActionListener(selectGameObject(BuildingType.BASE));
        Dimension dimension = new Dimension(70, 70);
        for (Button button : Arrays.asList(
                gold,
                goldMiner,
                base,
                warrior,
                resetChoice,
                cleanField,
                firstUser,
                secondUser)) {
            button.setSize(dimension);
            box.add(button);
        }
        // box.setSize(500, 80);
        // setSize(500, 80);
        add(box);
    }

    private ActionListener selectGameObject(GameObjectType gameObjectType) {
        return e -> this.actionDispatcher.dispatch(new SelectGameObjectAction(gameObjectType));
    }

    private ActionListener selectPlayer(Player player) {
        return e -> this.actionDispatcher.dispatch(new SelectPlayerAction(player));
    }

    private ActionListener reset() {
        return e -> {
            this.actionDispatcher.dispatch(new SelectGameObjectAction(null));
            this.actionDispatcher.dispatch(new SelectPlayerAction(null));
        };
    }

    // TODO: This is a test method. Remove it
    private void killAllObject() {
        java.util.List<GameObject> objects = this.board.getGameObjects();
        objects.forEach(object -> object.doDamage(100500));
    }
}
