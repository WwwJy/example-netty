package com.demo.io.service.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by w景洋
 * on 2019/9/11
 */
public class NettyChatClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        NioEventLoopGroup client = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientChannel());
            Channel channel = bootstrap.connect("localhost",8090).sync().channel();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                channel.writeAndFlush(buffer.readLine() + "\r\n");
            }
        }finally {
            client.shutdownGracefully();
        }
    }
}
