package com.dreamteam.powerofwar.connection.codec;

import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.Message;

public interface Decoder<T extends Message> extends Codec<T> {

    /**
     * Decodes the message from the specified byte buffer.
     * The process starts from the current position of the specified byte buffer.
     *
     * @param byteBuffer the buffer to read from.
     * @return the decoded message.
     */
    T decode(ByteBuffer byteBuffer);
}
