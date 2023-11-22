package com.dreamteam.powerofwar.connection.codec;

import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.message.Message;

/**
 * Implementations can decode the specified type of messages.
 *
 * @param <T> message type
 */
public abstract class Decoder<T extends Message> implements Codec<T> {

    /**
     * Decodes a message from the specified byte buffer.
     * The process starts from the current position of the specified byte buffer.
     *
     * @return a decoded message.
     */
    public abstract T decode(ByteBuffer byteBuffer);
}
