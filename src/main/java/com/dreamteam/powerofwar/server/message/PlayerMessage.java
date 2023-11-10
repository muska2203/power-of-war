package com.dreamteam.powerofwar.server.message;

import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.server.game.player.Player;

public abstract class PlayerMessage implements Message {

    private Player initiator;

    public Player getInitiator() {
        return initiator;
    }

    public void setInitiator(Player initiator) {
        this.initiator = initiator;
    }
}
