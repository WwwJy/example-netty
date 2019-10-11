package com.demo.io.service.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by w景洋
 * on 2019/10/10
 */
public class NioTest5 {

    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();

        // 循环监听5个端口的链接事件
        for (int i=0; i<ports.length; i++){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // false -- 非阻塞配置
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口: " + ports[i]);
        }

        // 处理链接建立事件 和 数据读取事件
        while (true){
            int numbers = selector.select();
            System.out.println("numbers: " + numbers);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectKey: " + selectionKeys);
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            // 遍历每个selectionKey  -- selectionKey 是每个channel注册selector到之后返回的标示，通过key可以获得异步事件
            while (iter.hasNext()){
                SelectionKey key = iter.next();
                // socket链接建立事件处理
                if (key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    iter.remove();
                    System.out.println("获得客户端连接: " + socketChannel);

                // 客户端向服务端发送数据事件处理
                }else if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    int byteRead = 0;
                    while (true){
                        // 申请一块512字节空间的内存，这块空间将会保存客户端发过来的数据
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        System.out.println("read: " + read);
                        if (read<0){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteRead += read;
                    }
                    System.out.println("读取: " + byteRead + "来自于: " + socketChannel);
                    iter.remove();
                }
            }
        }
    }
}
