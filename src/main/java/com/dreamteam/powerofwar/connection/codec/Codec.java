package com.dreamteam.powerofwar.connection.codec;

import com.dreamteam.powerofwar.connection.Message;

/**
 * Main interface which unites all encoders and decoders under it.
 *
 * @param <T> type of a message to work on.
 */
public interface Codec<T extends Message> {

    byte[] START_MESSAGE = new byte[]{(byte) 77, (byte) 77, (byte) 77};
    byte[] END_MESSAGE = new byte[]{(byte) 42, (byte) 42, (byte) 42};

    /**
     * Returns the count of bytes which needs to encode or decode a message with current type.
     * TODO: Must be researched to find out if it really needs. Now coding size can be different for one message. For example List of smth.
     *
     * @return count of bytes.
     */
    int getCodingSize();

    /**
     * Returns opCode. Uses for identification of message type which current codec work on.
     *
     * @return
     */
    OPCode getOPCode();
}
