package com.dreamteam.powerofwar.connection.session;

import java.util.Arrays;

import static com.dreamteam.powerofwar.connection.codec.Codec.START_MESSAGE;
import static com.dreamteam.powerofwar.connection.utils.ArrayUtils.*;
import static com.dreamteam.powerofwar.connection.utils.ByteUtils.decodeInt;

public class ChunkReader {

    private byte[] bytes = new byte[0];

    public void addChunk(byte[] chunk) {
        bytes = mergeArrays(bytes, chunk);
    }

    /**
     * @return null is message is not ready, otherwise encoded message as byte array
     */
    public byte[] getReadyToParseChunk() {
        int left = getFirstPosition(this.bytes, START_MESSAGE);
        if (bytes.length - START_MESSAGE.length - left < Integer.BYTES) {
            // If the count of the message bytes is zero,
            //      the received message will contain only START_MESSAGE.length + Integer.BYTES bytes.
            //      As result the message will be valid. We need receive at least this count of message to start to process it
            return null;
        }

        // trying to get count of the message bytes
        byte[] countEncoded = Arrays.copyOfRange(bytes, left + START_MESSAGE.length, left + START_MESSAGE.length + Integer.BYTES);
        int countBytes = decodeInt(countEncoded);
        int right = left + START_MESSAGE.length + Integer.BYTES + countBytes;

        if (right > this.bytes.length) {
            // if we need more bytes than we have now.
            return null;
        }
        byte[] message = Arrays.copyOfRange(this.bytes, left, right);
        this.bytes = exclude(this.bytes, left, right - 1);
        return message;
    }
}
