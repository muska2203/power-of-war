package com.dreamteam.powerofwar.connection.clienttest;

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
import com.dreamteam.powerofwar.connection.clienttest.message.PrintTextMessage;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.Codec;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.session.ChannelSession;
import com.dreamteam.powerofwar.connection.session.Session;
import com.dreamteam.powerofwar.handler.Dispatcher;
import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.handler.RegistryDispatcher;

@SpringBootApplication(scanBasePackages = {
        "com.dreamteam.powerofwar.connection.clienttest"
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
            while (true) {
                String word = consoleReader.readLine();
                clientConnection.sendMessage(new PrintTextMessage(word));
            }
        } catch (IOException e) {
            System.out.println("Connection has been refused.");
        }
    }

    @Bean
    Dispatcher<Message> registryMessageDispatcher(List<Handler<? extends Message>> handlers) {
        return new RegistryDispatcher<>(handlers);
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
    Session session(Dispatcher<Message> messageDispatcher, CodecDispatcher codecDispatcher) throws IOException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress(ConnectionInfo.IP, ConnectionInfo.PORT));
        return new ChannelSession(channel, messageDispatcher, codecDispatcher);
    }
}
