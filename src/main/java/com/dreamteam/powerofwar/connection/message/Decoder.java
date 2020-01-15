package com.dreamteam.powerofwar.connection.message;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Decoder<T extends Message> extends GenericHandler<T> {

    /**
     * Decodes the message from the specified byte buffer.
     * The process starts from the current position of the specified byte buffer.
     *
     * @param byteBuffer the buffer to read from.
     * @return the decoded message.
     * @throws IOException if buffer doesn't have enough bytes to read.
     */
    T decode(ByteBuffer byteBuffer) throws IOException;
}
