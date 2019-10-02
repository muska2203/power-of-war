package com.dreamteam.powerofwar.connection.client;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.dreamteam.powerofwar.connection.message.Message;

public class ClientConnection implements Closeable {

    private static SocketChannel clientSocketChannel;

    public ClientConnection(InetSocketAddress inetSocketAddress) throws IOException {
        clientSocketChannel = SocketChannel.open(inetSocketAddress);
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
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(message);
            byte[] array = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(array);
            clientSocketChannel.write(buffer);
            buffer.clear();
        } catch (IOException ignore) {
        }
    }
}
