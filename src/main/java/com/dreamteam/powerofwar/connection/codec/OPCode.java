package com.dreamteam.powerofwar.connection.codec;

/**
 * Contains a code of a client-server message.
 */
public interface OPCode {

        // So, let's increase it, if we have more than Byte.MAX_VALUE OPCodes
        int OP_CODE_SIZE = 1;

    /**
     * Returns a code of client-server message.
     * This code uses to identify the message type.
     *
     * @return code of client-server message
     */
    int getCode();
}
