package com.dreamteam.powerofwar.connection.session;

import com.dreamteam.powerofwar.connection.Message;

/**
 * A message containing information about sender session.
 */
public abstract class IncomingMessage implements Message {

    /**
     * Identifier of the session that sent the message.
     * <br>Setts automatically by session.
     */
    private Integer senderSessionId;

    public Integer getSenderSessionId() {
        return senderSessionId;
    }

    protected void setSenderSessionId(Integer senderSessionId) {
        this.senderSessionId = senderSessionId;
    }
}
