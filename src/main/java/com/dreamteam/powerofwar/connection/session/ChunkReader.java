package com.dreamteam.powerofwar.connection.session;

import java.util.Arrays;

import static com.dreamteam.powerofwar.connection.codec.Codec.END_MESSAGE;
import static com.dreamteam.powerofwar.connection.codec.Codec.START_MESSAGE;
import static com.dreamteam.powerofwar.connection.utils.ArrayUtils.*;
import static com.dreamteam.powerofwar.connection.utils.ByteUtils.getInt;

public class ChunkReader {

    private byte[] bytes = new byte[0];

    public void addChunk(byte[] chunk) {
        System.out.println("addChunk -> " + Arrays.toString(chunk));
        bytes = mergeArrays(bytes, chunk);
    }

    /**
     * @return null is message is not ready, otherwise encoded message as byte array
     */
    public byte[] getReadyToParseChunk() {
        int left = getFirstPosition(this.bytes, START_MESSAGE);
        System.out.println("left: " + left);
        if (bytes.length - START_MESSAGE.length - left < 4) {
            return null;
        }
        byte[] countEncoded = Arrays.copyOfRange(bytes, left + START_MESSAGE.length, left + START_MESSAGE.length + 4);
        int countBytes = getInt(countEncoded);
        System.out.println("int: " + countBytes + ", countEncoded: " + Arrays.toString(countEncoded));
        int right = left + START_MESSAGE.length + Integer.BYTES + countBytes;
        if (right >= this.bytes.length) {
            return null;
        }
        byte[] message = Arrays.copyOfRange(this.bytes, left, right);
        System.out.println("Message: " + Arrays.toString(message));
        this.bytes = exclude(this.bytes, left, right - 1);
        return message;
    }
}
