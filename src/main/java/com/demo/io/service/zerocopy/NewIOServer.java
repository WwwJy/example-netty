package com.demo.io.service.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by w景洋
 * on 2019/10/12
 */
public class NewIOServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8090);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);
            int readCount = 0;
            while (-1 != readCount){
                try {
                    readCount = socketChannel.read(byteBuffer);
                }catch (Exception e){
                    e.printStackTrace();
                }
                byteBuffer.rewind();
            }
        }
    }
}
