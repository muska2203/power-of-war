package com.dreamteam.powerofwar.connection.message.handler;

import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.message.type.CloseConnectionMessage;
import org.springframework.stereotype.Component;

@Component
public class CloseConnectionMessageHandler implements MessageHandler<CloseConnectionMessage> {


    @Override
    public void handle(CloseConnectionMessage message) {
        System.out.println("CloseConnectionMessage handled");
    }
}
