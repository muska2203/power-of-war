package com.dreamteam.powerofwar.connection.codec;

import com.dreamteam.powerofwar.connection.Message;

public interface Codec<T extends Message> {

    // TODO: Пересмотреть использование данного метода. размер сообщения может быть не определен из-за разного набора данных например в списке
    int getCodingSize();

    OPCode getOPCode();
}
