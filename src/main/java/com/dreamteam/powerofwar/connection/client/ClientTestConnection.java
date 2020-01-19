package com.dreamteam.powerofwar.connection.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.connection.ConnectionInfo;
import com.dreamteam.powerofwar.connection.client.message.PrintTextMessage;
import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.MessageDispatcher;
import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.message.RegistryMessageDispatcher;
import com.dreamteam.powerofwar.connection.message.codec.Codec;
import com.dreamteam.powerofwar.connection.message.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.message.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.server.message.handler.PrintTextMessageHandler;
import com.dreamteam.powerofwar.connection.message.session.ChannelSession;

@SpringBootApplication(scanBasePackages = {
        "com.dreamteam.powerofwar.connection.message",
        "com.dreamteam.powerofwar.connection.client"
})
public class ClientTestConnection {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientTestConnection.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run(args);

        ;
        try (ClientConnection clientConnection = context.getBean(ClientConnection.class)) {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (clientConnection.isOpen()) {
                String word = consoleReader.readLine();
                clientConnection.sendMessage(new PrintTextMessage(word));
            }
        } catch (IOException e) {
            System.out.println("Connection has been refused.");
        }
    }

    @Bean
    public PrintTextMessageHandler closeConnectionMessageHandler() {
        return new PrintTextMessageHandler();
    }

    @Bean
    MessageDispatcher registryMessageDispatcher(List<MessageHandler<? extends Message>> handlers) {
        return new RegistryMessageDispatcher(handlers);
    }

    @Bean
    CodecDispatcher codecDispatcher(List<Codec<?>> codecs) {
        RegistryCodecDispatcher dispatcher = new RegistryCodecDispatcher();
        for (Codec<?> codec : codecs) {
            dispatcher.register(codec);
        }
        return dispatcher;
    }

    @Bean
    ClientConnection clientConnection(MessageDispatcher messageDispatcher, CodecDispatcher codecDispatcher) throws IOException {
        return new ClientConnection(new InetSocketAddress(ConnectionInfo.IP, ConnectionInfo.PORT)) {
            @Override
            ChannelSession createChannelSession(SocketChannel socketChannel) {
                return new ChannelSession(socketChannel, messageDispatcher, codecDispatcher);
            }
        };
    }
}
