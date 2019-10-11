package com.demo.io.service.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by w景洋
 * on 2019/10/11
 */
public class NioTestService {
    private static Map<String,SocketChannel> clientMap = new HashMap<>();
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1",8090));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey ->{
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);
                            String key = UUID.randomUUID().toString();
                            clientMap.put(key,client);
                            selectionKeys.remove(selectionKey);
                        } else if (selectionKey.isReadable()){
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readyBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readyBuffer);
                            if (count > 0){
                                readyBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String receiveMessage = String.valueOf(charset.decode(readyBuffer).array());
                                System.out.println(client + ":" + receiveMessage);

                                String senderKey = null;
                                for (Map.Entry<String, SocketChannel> entry: clientMap.entrySet()){
                                    if (client == entry.getValue()){
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
                                    SocketChannel val = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((senderKey + ":" + receiveMessage).getBytes());
                                    writeBuffer.flip();
                                    val.write(writeBuffer);
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
