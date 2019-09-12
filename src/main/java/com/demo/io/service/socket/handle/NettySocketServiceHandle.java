package com.demo.io.service.socket.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created by w景洋
 * on 2019/9/11
 */
public class NettySocketServiceHandle extends SimpleChannelInboundHandler<String> {

    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "," + s);
        ctx.writeAndFlush("from server : " + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
