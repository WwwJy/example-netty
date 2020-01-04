package com.demo.io.service.custom.handle;

import com.demo.io.service.custom.protocol.PersonProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServiceHandle extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] b = msg.getContent();
        System.out.println("服务端接收到的数据: ");
        System.out.println("长度: " + length);
        System.out.println("内容: " + new String(b, Charset.forName("utf-8")));
        System.out.println("服务端接收到的消息数量: " + (++count));
        String response = UUID.randomUUID().toString();
        byte[] resp = response.getBytes(Charset.forName("utf-8"));
        PersonProtocol p = new PersonProtocol();
        p.setLength(resp.length);
        p.setContent(resp);
        ctx.writeAndFlush(p);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        ctx.close();
    }
}
