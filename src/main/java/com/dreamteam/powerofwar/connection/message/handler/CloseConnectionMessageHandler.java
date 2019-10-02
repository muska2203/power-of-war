package com.dreamteam.powerofwar.connection.message.handler;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.message.type.CloseConnectionMessage;

@Component
public class CloseConnectionMessageHandler implements MessageHandler<CloseConnectionMessage> {

    @Override
    public void handle(CloseConnectionMessage message) {
        System.out.println("CloseConnectionMessage handled");
    }
}
