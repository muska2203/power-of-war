package com.dreamteam.powerofwar.connection.codec;

import com.dreamteam.powerofwar.connection.Message;

/**
 * Main interface which unites all encoders and decoders under it.
 *
 * @param <T> type of a message to work on.
 */
public interface Codec<T extends Message> {

    byte[] START_MESSAGE = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1};

    /**
     * Returns opCode. Uses for identification of message type which current codec work on.
     *
     * @return
     */
    OPCode getOPCode();
}
