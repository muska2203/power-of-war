package com.dreamteam.powerofwar.client.ui.playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.player.Player;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InfoBarComponent extends JComponent {

    private Player player;
    // TODO: удалить board после исправления размеров компонента
    private Board board;

    public InfoBarComponent(Player player, Board board) {
        super();
        this.player = player;
        this.board = board;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension((int) this.board.getWidth(), 25);
    }

    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = getSize();
        g.setColor(Color.black);
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(Color.white);
        g.drawString("Gold: " + this.player.getContext().getResource(ResourceType.GOLD), 0, 10);
        g.drawString("Warriors: " + this.player.getContext().getObjectCount(UnitType.WARRIOR), 0, 20);
        g.drawString("Gold Miners: " + this.player.getContext().getObjectCount(UnitType.GOLD_MINER), 100, 20);
    }
}
