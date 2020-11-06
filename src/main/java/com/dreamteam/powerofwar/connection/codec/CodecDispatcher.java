package com.dreamteam.powerofwar.connection.codec;

import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.Message;

/**
 * Uses to delegate choosing of the necessary codec.
 */
public interface CodecDispatcher {

    /**
     * Decodes a message from the specified byte buffer.
     *
     * @param byteBuffer a byte buffer to read a message from.
     * @return filled a message with necessary type.
     */
    Message decode(ByteBuffer byteBuffer);

    /**
     * Encodes the specified message to the specified byte buffer.
     *
     * @param byteBuffer a byte buffer to write message to
     * @param message a message to write
     * @param <T> some type of the message
     * @return {@code true} if writing has been completed successfully.
     */
    <T extends Message> boolean encode(ByteBuffer byteBuffer, T message);
}
