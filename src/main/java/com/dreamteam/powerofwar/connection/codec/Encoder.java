package com.dreamteam.powerofwar.connection.codec;

import com.dreamteam.powerofwar.connection.Message;

import java.nio.ByteBuffer;

/**
 * Implementations can encode the specified type of messages.
 * @param <T> message type
 */
public abstract class Encoder<T extends Message> implements Codec<T> {

    /**
     * Encodes the message to the specified byte buffer.
     * The process starts from the current position of the specified byte array.
     *
     * @param byteBuffer a buffer to write to.
     * @param message a message to encode.
     * @return the buffer after writing.
     */
    public boolean encode(ByteBuffer byteBuffer, T message) {
        byteBuffer.put(START_MESSAGE);
        byteBuffer.put(encode(message));
        byteBuffer.put(END_MESSAGE);
        return true;
    }

    private byte[] encode(T message) {
        ByteBuffer buffer = ByteBuffer.allocate(getMessageSize(message));
        write(message, buffer);
        return buffer.array();
    }

    protected abstract void write(T message, ByteBuffer buffer);

    public abstract int getMessageSize(T message);
}
