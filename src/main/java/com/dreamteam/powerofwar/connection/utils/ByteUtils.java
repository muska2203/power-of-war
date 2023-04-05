package com.dreamteam.powerofwar.connection.utils;

public final class ByteUtils {

    private ByteUtils() {}

    public static int decodeInt(byte[] bytes) {
        return ((int) bytes[0] << 24)
                | ((int) bytes[1] << 24 >>> 8)
                | ((int) bytes[2] << 24 >>> 16)
                | ((int) bytes[3] << 24 >>> 24);
    }

    public static byte[] encodeInt(int x) {
        return new byte[] {
                (byte) (x >>> 24),
                (byte) (x << 8 >>> 24),
                (byte) (x << 16 >>> 24),
                (byte) x
        };
    }

}
