package com.dreamteam.powerofwar.connection.servertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.Codec;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.session.ChannelSession;
import com.dreamteam.powerofwar.handler.Dispatcher;
import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.handler.RegistryDispatcher;

@SpringBootApplication(scanBasePackages = {
        "com.dreamteam.powerofwar.game",
        "com.dreamteam.powerofwar.connection.servertest"
})
public class ServerTestConnection {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ServerTestConnection.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run(args);

        ServerConnection server = context.getBean(ServerConnection.class);
        server.start();
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message = reader.readLine();
            server.sendMessage(message);
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
        }
    }

    @Bean
    Dispatcher<Message> registryMessageDispatcher(List<Handler<? extends Message>> handlers) {
        return new RegistryDispatcher<>(handlers);
    }

    @Bean
    CodecDispatcher codecDispatcher(List<Codec<?>> codecs) {
        RegistryCodecDispatcher codecDispatcher = new RegistryCodecDispatcher();
        for (Codec<?> codec : codecs) {
            codecDispatcher.register(codec);
        }
        return codecDispatcher;
    }

    @Bean
    ServerConnection serverConnection(Dispatcher<Message> messageDispatcher, CodecDispatcher codecDispatcher) throws IOException {
        return new ServerConnection() {
            @Override
            ChannelSession createChannelSession(SocketChannel socketChannel) {
                return new ChannelSession(socketChannel, messageDispatcher, codecDispatcher);
            }
        };
    }

}
