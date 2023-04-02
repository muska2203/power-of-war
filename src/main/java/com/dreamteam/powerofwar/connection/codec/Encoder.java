package com.dreamteam.powerofwar.connection.codec;

import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.Message;

/**
 * Implementations can encode the specified type of messages.
 * @param <T> message type
 */
public interface Encoder<T extends Message> extends Codec<T> {

    /**
     * Encodes the message to the specified byte buffer.
     * The process starts from the current position of the specified byte array.
     *
     * @param byteBuffer a buffer to write to.
     * @param message a message to encode.
     * @return the buffer after writing.
     */
    boolean encode(ByteBuffer byteBuffer, T message);

    int getMessageSize(T message);
}
