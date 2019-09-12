package com.demo.io.service.socket.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * Created by w景洋
 * on 2019/9/11
 */
public class NettySocketClientHandle extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 测试代码， 当连接建立并处于活跃状态时,客户端主动向服务器端发送一条消息
        // 服务器收到客户端消息会进行固定返回，客户端收到消息继续回应服务器。
        ctx.writeAndFlush("from client : Hello World");
    }

    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output : " + s);
        ctx.writeAndFlush("from client : " + LocalDateTime.now());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
