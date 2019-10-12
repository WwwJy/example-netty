package com.demo.io.service.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by w景洋
 * on 2019/10/12
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8090));
        socketChannel.configureBlocking(true);

        String fileName = "";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long startTime = System.currentTimeMillis();
        long transferCount = fileChannel.transferTo(0,fileChannel.size(),socketChannel);
        System.out.println("发送的总字节数: " +transferCount + "耗时: " + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
