package com.dreamteam.powerofwar.connection.utils;

import javax.swing.plaf.basic.BasicBorders;
import java.util.Arrays;

public final class ByteUtils {

    private ByteUtils() {}

    public static int getInt(byte[] bytes) {
        if (bytes.length < 4) {
            throw new RuntimeException("Cannot getInt. bytes.length < 4");
        }

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

    public static void main(String[] args) {
        // 00000000 00000000 00000100 11100111
        System.out.println(Integer.toBinaryString(1255));
        System.out.println(Arrays.toString(encodeInt(1255)));
        System.out.println(getInt(encodeInt(1255)));
    }
}
