package com.demo.io.service.nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by w景洋
 * on 2019/10/11
 */
public class NioTestClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8090));
            while (true){
                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();
                for (SelectionKey selectionKey : keySet){
                    if (selectionKey.isConnectable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if (client.isConnectionPending()){
                            client.finishConnect();
                            ByteBuffer writeBuff = ByteBuffer.allocate(1024);
                            writeBuff.put((LocalDateTime.now() + "连接成功").getBytes());
                            writeBuff.flip();
                            client.write(writeBuff);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() ->{
                               while (true){
                                    try {
                                        writeBuff.clear();
                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        String message = bufferedReader.readLine();
                                        writeBuff.put(message.getBytes());
                                        writeBuff.flip();
                                        client.write(writeBuff);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                               }
                            });
                            keySet.remove(selectionKey);
                        }
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }else if (selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer);
                        if (count > 0){
                            String receiver = new String(readBuffer.array(), 0, count);
                            System.out.println(receiver);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
