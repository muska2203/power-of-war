package com.dreamteam.powerofwar.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.state.GenericMutableState;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.connection.ConnectionInfo;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.Codec;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.session.ChannelSession;
import com.dreamteam.powerofwar.connection.session.IncomingMessage;
import com.dreamteam.powerofwar.connection.session.Session;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.handler.Dispatcher;
import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.handler.RegistryDispatcher;

@SpringBootApplication(scanBasePackages = {"com.dreamteam.powerofwar.client", "com.dreamteam.powerofwar.game"})
public class ClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientApplication.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run(args);

//        GameProgram gameProgram = context.getBean(GameProgram.class);
//        gameProgram.startGame();
    }

    @Bean
    public GenericMutableState<SelectedPlayer> selectedPlayerState() {
        return new GenericMutableState<>();
    }

    @Bean
    public GenericMutableState<SelectedGameObject> selectedGameObjectState() {
        return new GenericMutableState<>();
    }

    @Bean
    public Dispatcher<Action> actionDispatcher(List<Handler<? extends Action>> handlers) {
        return new RegistryDispatcher<>(handlers);
    }

    @Bean
    public Board board() {
        return new Board();
    }

    @Bean
    public GameProgram gameProgram(Board board) {
        return new GameProgram(board);
    }

    @Bean
    public Dispatcher<IncomingMessage> messageDispatcher(List<Handler<? extends IncomingMessage>> handlers) {
        return new RegistryDispatcher<>(handlers);
    }

    @Bean
    public CodecDispatcher codecDispatcher(List<Codec<?>> codecs) {
        return new RegistryCodecDispatcher(codecs);
    }

    @Bean
    public Session session(Dispatcher<IncomingMessage> messageDispatcher, CodecDispatcher codecDispatcher) throws IOException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress(ConnectionInfo.IP, ConnectionInfo.PORT));
        return new ChannelSession(channel, messageDispatcher, codecDispatcher);
    }

    @Bean
    public ClientConnection clientConnection(Session session) {
        return new ClientConnection(session);
    }
}
