package com.dreamteam.powerofwar.connection.servertest.message.handler;


import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.servertest.message.PrintTextMessage;
import com.dreamteam.powerofwar.handler.Handler;

@Component
public class PrintTextMessageHandler implements Handler<PrintTextMessage> {

    @Override
    public void handle(PrintTextMessage message) {
        System.out.println(message.getTextMessage());
    }
}
