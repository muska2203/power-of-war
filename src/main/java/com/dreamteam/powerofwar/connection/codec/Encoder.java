package com.dreamteam.powerofwar.connection.codec;

import com.dreamteam.powerofwar.connection.Message;

import java.nio.ByteBuffer;

import static com.dreamteam.powerofwar.connection.utils.ArrayUtils.mergeArrays;
import static com.dreamteam.powerofwar.connection.utils.ByteUtils.encodeInt;

/**
 * Implementations can encode the specified type of messages.
 * @param <T> message type
 */
public abstract class Encoder<T extends Message> implements Codec<T> {

    /**
     * Encodes the message to the specified byte array.
     * The process starts from the current position of the specified byte array.
     *
     * @param message a message to encode.
     * @return the buffer after writing.
     */
    public byte[] encode(T message) {

        byte[] mainMassage = mergeArrays(
                new byte[]{ (byte) getOPCode().getCode() },
                encodeInternal(message)
        );
        return mergeArrays(
                START_MESSAGE,
                encodeInt(mainMassage.length),
                mainMassage
        );
    }

    private byte[] encodeInternal(T message) {
        ByteBuffer buffer = ByteBuffer.allocate(getMessageSize(message));
        write(message, buffer);
        return buffer.array();
    }

    protected abstract void write(T message, ByteBuffer buffer);

    public abstract int getMessageSize(T message);
}
