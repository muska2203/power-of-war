package com.dreamteam.powerofwar.connection.servertest.message;

import com.dreamteam.powerofwar.connection.codec.OPCode;

public enum MessageTypes implements OPCode {
    PRINT_TEXT_MESSAGE(1)
    ;
    private int code;

    MessageTypes(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
