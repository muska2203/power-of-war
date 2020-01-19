package com.dreamteam.powerofwar.connection.message.codec;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.message.Message;

public interface Codec<T extends Message> {

    /**
     * Decodes the message from the specified byte buffer.
     * The process starts from the current position of the specified byte buffer.
     *
     * @param byteBuffer the buffer to read from.
     * @return the decoded message.
     * @throws IOException if buffer doesn't have enough bytes to read.
     */
    default T decode(ByteBuffer byteBuffer) throws IOException {
        throw new UnsupportedOperationException("Encode for message type" + getHandledClass() + " not supported");
    }

    /**
     * Encodes the message to the specified byte buffer.
     * The process starts from the current position of the specified byte array.
     *
     * @param byteBuffer the buffer to write to.
     * @param message the message to encode.
     * @return the buffer after writing.
     * @throws IOException if buffer doesn't have enough bytes to write or any message data fails to encode.
     */
    default boolean encode(ByteBuffer byteBuffer, T message) throws IOException {
        throw new UnsupportedOperationException("Encode for message type" + getHandledClass() + " not supported");
    }

    int codingSize();

    OPCode getOPCode();

    Class<T> getHandledClass();
}
