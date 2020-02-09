package com.dreamteam.powerofwar.connection.codec;


import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.Message;

public interface Encoder<T extends Message> extends Codec<T> {

    /**
     * Encodes the message to the specified byte buffer.
     * The process starts from the current position of the specified byte array.
     *
     * @param byteBuffer the buffer to write to.
     * @param message the message to encode.
     * @return the buffer after writing.
     */
    boolean encode(ByteBuffer byteBuffer, T message);
}
