package com.dreamteam.powerofwar.connection.utils;

import javax.swing.plaf.basic.BasicBorders;

public final class ByteUtils {

    private ByteUtils() {}

    public static int getInt(byte[] bytes) {
        if (bytes.length < 4) {
            throw new RuntimeException("Cannot getInt. bytes.length < 4");
        }

        return (bytes[0] << 24)
                | (bytes[1] << 16)
                | (bytes[2] << 8)
                | (bytes[3]);
    }

    public static byte[] encodeInt(int x) {
        return new byte[] {
                (byte) ((x & 0xFF000000) >> 24),
                (byte) ((x & 0x00FF0000) >> 16),
                (byte) ((x & 0x0000FF00) >> 8),
                (byte) ((x & 0x000000FF))
        };
    }
}
