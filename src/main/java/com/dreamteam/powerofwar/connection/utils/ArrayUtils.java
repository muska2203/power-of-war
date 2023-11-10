package com.dreamteam.powerofwar.connection.utils;

public final class ArrayUtils {

    private ArrayUtils() {}

    public static byte[] mergeArrays(byte[] ...arrays) {
        int length = 0;
        for (byte[] arr : arrays) {
            length += arr.length;
        }

        byte[] result = new byte[length];
        int currPosition = 0;
        for (byte[] arr : arrays) {
            System.arraycopy(arr, 0, result, currPosition, arr.length);
            currPosition += arr.length;
        }

        return result;
    }
}
