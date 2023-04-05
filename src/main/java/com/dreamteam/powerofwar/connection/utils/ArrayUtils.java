package com.dreamteam.powerofwar.connection.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ArrayUtils {

    private ArrayUtils() {}

    public static List<byte[]> split(byte[] bytes, int maxLength) {
        int count = bytes.length / maxLength;
        if (bytes.length % maxLength > 0) {
            count++;
        }

        return IntStream.range(0, count)
                .mapToObj(chunkNum -> {
                    int left = chunkNum * maxLength;
                    int right = Math.min((chunkNum + 1) * maxLength, bytes.length);
                    return Arrays.copyOfRange(bytes, left, right);
                })
                .collect(Collectors.toList());
    }

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

    /**
     * //TODO: Improve algo
     * @return position of the subArray start, -1 otherwise
     */
    public static int getFirstPosition(byte[] src, byte[] subArray) {
        for (int i = 0; i < src.length - subArray.length + 1; i++) {
            boolean equals = true;
            for (int j = 0; j < subArray.length; j++) {
                if (src[i + j] != subArray[j]) {
                    equals = false;
                    break;
                }
            }
            if (equals) {
                return i;
            }
        }
        return -1;
    }

    public static byte[] exclude(byte[] array, int left, int right) {
        byte[] result = new byte[array.length - (right - left + 1)];
        int startPosition = 0;
        for (int i = 0; i < array.length; i++) {
            if (!(left <= i && i <= right)) {
                result[startPosition++] = array[i];
            }
        }
        return result;
    }
}
