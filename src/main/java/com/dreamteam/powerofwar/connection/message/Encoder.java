package com.dreamteam.powerofwar.connection.message;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Encoder<T extends Message> extends GenericHandler<T> {

    /**
     * Encodes the message to the specified byte buffer.
     * The process starts from the current position of the specified byte array.
     *
     * @param byteBuffer the buffer to write to.
     * @param message the message to encode.
     * @return the buffer after writing.
     * @throws IOException if buffer doesn't have enough bytes to write or any message data fails to encode.
     */
    ByteBuffer encode(ByteBuffer byteBuffer, T message) throws IOException;
}
