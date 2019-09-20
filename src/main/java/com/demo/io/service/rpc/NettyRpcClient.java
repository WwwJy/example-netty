package com.demo.io.service.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by w景洋
 * on 2019/9/20
 */
public class NettyRpcClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup client = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client)
                    .channel(NioSocketChannel.class)
                    .handler(new RpcClientChannel());
            ChannelFuture future = bootstrap.connect("localhost",8090).sync();
            future.channel().closeFuture().sync();

        }finally {
            client.shutdownGracefully();
        }
    }
}
