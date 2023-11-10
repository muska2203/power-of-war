package com.dreamteam.powerofwar.client.ui.playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.dreamteam.powerofwar.client.game.GameContext;
import com.dreamteam.powerofwar.game.types.ResourceType;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InfoBarComponent extends JComponent {

    private final Timer repaintTimer;
    private GameContext gameContext;

    public InfoBarComponent(GameContext gameContext) {
        super();
        this.gameContext = gameContext;
        this.repaintTimer = new Timer(20, e -> this.repaint());
        // TODO: Implement a game state. Start and stop the timer according to the state
        this.repaintTimer.start();
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension((int) this.gameContext.getWidth(), 25);
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
        g.drawString("Gold: " + this.gameContext.getResource(ResourceType.GOLD), 0, 10);
        g.drawString("Objects: " + this.gameContext.getGameObjects().size(), 0, 20);
    }
}
