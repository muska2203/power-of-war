package com.dreamteam.powerofwar.connection.codec;

import com.dreamteam.powerofwar.connection.Message;

public interface Codec<T extends Message> {

    int getCodingSize();

    OPCode getOPCode();
}
