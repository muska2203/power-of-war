package com.dreamteam.powerofwar.server.message;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.connection.Message;

public abstract class PlayerMessage implements Message {

    private Player initiator;

    public Player getInitiator() {
        return initiator;
    }

    public void setInitiator(Player initiator) {
        this.initiator = initiator;
    }
}
