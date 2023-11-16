package com.dreamteam.powerofwar.connection.codec;

import com.dreamteam.powerofwar.connection.Message;

/**
 * Main interface which unites all encoders and decoders under it.
 *
 * @param <T> type of the message to work on.
 */
public interface Codec<T extends Message> {

    /**
     * Uses for identification of message type which current codec work on.
     *
     * @return opCode
     */
    OPCode getOPCode();
}
