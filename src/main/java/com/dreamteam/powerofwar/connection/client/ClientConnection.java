package com.dreamteam.powerofwar.connection.client;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.session.ChannelSession;

public abstract class ClientConnection implements Closeable {

    private SocketChannel clientSocketChannel;
    private ChannelSession channelSession;

    public ClientConnection(InetSocketAddress inetSocketAddress) throws IOException {
        clientSocketChannel = SocketChannel.open(inetSocketAddress);
        channelSession = this.createChannelSession(clientSocketChannel);
    }

//    public void startListeningServer(ServerListener serverListener) {
//        new Thread(() -> {
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
//            try {
//                while (true) {
//                    int numRead = clientSocketChannel.read(buffer);
//                    byte[] data = new byte[numRead];
//                    System.arraycopy(buffer.array(), 0, data, 0, numRead);
//                    String message = new String(data);
////                    serverListener.registerMessage(message);
//                    buffer.clear();
//                }
//            } catch (AsynchronousCloseException ignore) {
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

    @Override
    public void close() throws IOException {
        clientSocketChannel.close();
    }

    public boolean isOpen() {
        return clientSocketChannel != null && clientSocketChannel.isOpen();
    }

    public void sendMessage(Message message) {
        try {
            channelSession.send(message);
        } catch (ConnectionClosedException e) {
            System.out.println("Connection has been closed");
        }
    }

    abstract ChannelSession createChannelSession(SocketChannel socketChannel);
}
