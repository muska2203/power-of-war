package com.dreamteam.powerofwar.client.ui.playground;

import javax.swing.Box;
import javax.swing.JPanel;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Arrays;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.action.type.SelectGameObjectAction;
import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.handler.Dispatcher;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ControlComponent extends JPanel {

    private Dispatcher<Action> actionDispatcher;

    public ControlComponent(Dispatcher<Action> actionDispatcher) {
        this.actionDispatcher = actionDispatcher;

        Box box = Box.createHorizontalBox();
        Button warrior = new Button("Warrior");
        Button cowardMinion = new Button("Coward Minion");
        Button resetChoice = new Button("Reset choice");
        Button goldMiner = new Button("Gold Miner");
        Button gold = new Button("Gold");
        Button base = new Button("Base");
        warrior.addActionListener(selectGameObject(GameObjectType.WARRIOR));
        cowardMinion.addActionListener(selectGameObject(GameObjectType.COWARD));
        resetChoice.addActionListener(reset());
        goldMiner.addActionListener(selectGameObject(GameObjectType.GOLD_MINER));
        gold.addActionListener(selectGameObject(GameObjectType.GOLD_MINE));
        base.addActionListener(selectGameObject(GameObjectType.BASE));
        Dimension dimension = new Dimension(70, 70);
        for (Button button : Arrays.asList(
                gold,
                goldMiner,
                base,
                warrior,
                resetChoice)) {
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

    private ActionListener reset() {
        return e -> {
            this.actionDispatcher.dispatch(new SelectGameObjectAction(null));
        };
    }
}
