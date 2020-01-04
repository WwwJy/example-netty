package com.demo.io.service.custom.handle;

import com.demo.io.service.custom.protocol.PersonProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i =0; i<10; i++) {
            String message = "send from client ";
            byte[] content = message.getBytes(Charset.forName("utf-8"));
            PersonProtocol p = new PersonProtocol();
            p.setContent(content);
            p.setLength(content.length);
            ctx.writeAndFlush(p);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        System.out.println("客户端收到的消息内容: ");
        System.out.println("长度: " + msg.getLength());
        System.out.println("内容: " + new String(msg.getContent(), Charset.forName("utf-8")));
        System.out.println("客户端接收的消息数量: " + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
