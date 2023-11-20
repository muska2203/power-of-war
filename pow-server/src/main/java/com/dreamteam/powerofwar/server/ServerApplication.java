package com.dreamteam.powerofwar.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.common.handler.Dispatcher;
import com.dreamteam.powerofwar.common.handler.Handler;
import com.dreamteam.powerofwar.common.handler.RegistryDispatcher;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.Codec;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.session.ChannelSession;
import com.dreamteam.powerofwar.server.game.GameProgram;
import com.dreamteam.powerofwar.server.game.event.EventListener;
import com.dreamteam.powerofwar.server.game.object.Board;
import com.dreamteam.powerofwar.server.game.player.GameObjectFactory;

@SpringBootApplication(scanBasePackages = {"com.dreamteam.powerofwar.server", "com.dreamteam.powerofwar.server.game"})
public class ServerApplication {

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ServerApplication.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run();

        GameProgram gameProgram = context.getBean(GameProgram.class);
        StateUpdater stateUpdater = context.getBean(StateUpdater.class);
        gameProgram.startGame();
        stateUpdater.start();
    }

    @Bean
    public GameObjectFactory gameObjectFactory() {
        return new GameObjectFactory();
    }

    @Bean
    public Board board(GameObjectFactory gameObjectFactory) {
        return new Board(gameObjectFactory);
    }

    @Bean
    public GameProgram gameProgram(Board board) {
        return new GameProgram(board);
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
    ServerConnection serverConnection(Dispatcher<Message> registryMessageDispatcher, EventListener eventListener,
                                      CodecDispatcher codecDispatcher) throws IOException {
        return new ServerConnection(registryMessageDispatcher, eventListener) {
            @Override
            ChannelSession createChannelSession(SocketChannel socketChannel) {
                return new ChannelSession(socketChannel, codecDispatcher);
            }
        };
    }
}
