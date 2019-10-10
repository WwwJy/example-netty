package com.demo.io.service.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于buffer的Scattering 与 Gathering
 */
public class NioTest4 {

    public static void main(String[] args) throws IOException {
        // 建立socket 服务端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8090);
        serverSocketChannel.socket().bind(address);
        // 分配缓存区大小，建立数组
        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        // 分配缓冲区大小
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);
        // 阻塞服务端
        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true){
            int byteRead = 0;
            while (byteRead < messageLength){
                long r = socketChannel.read(buffers);
                byteRead += r;
                System.out.println("byteRead : " + byteRead);
                Arrays.asList(buffers)
                        .stream()
                        .map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit())
                        .forEach(System.out::println);
            }
            Arrays.asList(buffers).forEach(buffer -> {
                buffer.flip();
            });
            long bytesWritten = 0;
            while (bytesWritten < messageLength){
                long r = socketChannel.write(buffers);
                bytesWritten += r;
            }
            Arrays.asList(buffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("byteRead: " + byteRead + ", bytesWritten :" + bytesWritten + ", messageLength: " + messageLength);
        }
    }
}
