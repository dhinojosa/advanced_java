package com.xyzcorp.demos.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class EchoClient {
    private static SocketChannel socketChannel;
    private static ByteBuffer buffer;
    private static EchoClient instance;

    public synchronized static EchoClient start() {
        if (instance == null)
            instance = new EchoClient();

        return instance;
    }

    public synchronized  static void stop() throws IOException {
        socketChannel.close();
        buffer = null;
    }

    private EchoClient() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 5454));
            socketChannel.finishConnect();
            buffer = ByteBuffer.allocate(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String response = null;
        try {
            socketChannel.write(buffer);
            buffer.clear();
            socketChannel.read(buffer);
            response = new String(buffer.array()).trim();
            System.out.println("response=" + response);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String[] args) throws IOException {
        EchoClient echoClient = EchoClient.start();
        String resp1 = echoClient.sendMessage("hello");
        String resp2 = echoClient.sendMessage("world");
        System.out.println(resp1);
        System.out.println(resp2);
        EchoClient.stop();
    }
}
