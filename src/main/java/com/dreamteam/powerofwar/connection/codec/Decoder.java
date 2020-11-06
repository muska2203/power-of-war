package com.dreamteam.powerofwar.connection.codec;

import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.Message;

/**
 * Implementations can decode the specified type of messages.
 * @param <T> message type
 */
public interface Decoder<T extends Message> extends Codec<T> {

    /**
     * Decodes a message from the specified byte buffer.
     * The process starts from the current position of the specified byte buffer.
     *
     * @param byteBuffer a buffer to read from.
     * @return a decoded message.
     */
    T decode(ByteBuffer byteBuffer);
}
