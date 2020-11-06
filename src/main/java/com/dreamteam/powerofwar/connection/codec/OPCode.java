package com.dreamteam.powerofwar.connection.codec;

/**
 * Contains a code of a client-server message.
 */
public interface OPCode {

    /**
     * Returns a code of client-server message.
     * This code uses to identify the message type.
     *
     * @return code of client-server message
     */
    int getCode();
}
