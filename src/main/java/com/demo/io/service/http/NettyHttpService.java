package com.demo.io.service.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by w景洋
 * on 2019/9/9
 */
public class NettyHttpService {
    public static void main(String[] args) throws InterruptedException {
        // 事件循环组
        // boss - 用来接收客户端连接，但是不做其他处理，把任务交给了worker
        // worker - 主要用于不断轮寻读取数据以及业务逻辑处理
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 用于启动服务端的类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    // 线程组，告诉netty 哪个线程接收连接，哪个线程处理业务。
                    .group(boss,worker)
                    // 管道，源码用反射的方式创建了，这里我们也可以选择http的管道。
                    .channel(NioServerSocketChannel.class)
                    // 自定义初始化器
                    // handler -> 针对的是boss线程的消息拦截处理，包括日志打印等
                    // childHandler -> 针对的是worker线程的消息拦截处理，包括路由，日志，逻辑执行等。
//                    .handler()
                    .childHandler(new HttpServiceChannel());
            ChannelFuture channelFuture = serverBootstrap.bind("localhost",8090).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
