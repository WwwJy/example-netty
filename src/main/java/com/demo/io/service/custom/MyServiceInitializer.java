package com.demo.io.service.custom;

import com.demo.io.service.custom.handle.MyServiceHandle;
import com.demo.io.service.custom.protocol.MyPersonDecoder;
import com.demo.io.service.custom.protocol.MyPersonEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServiceInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyPersonDecoder());
        pipeline.addLast(new MyPersonEncoder());
        pipeline.addLast(new MyServiceHandle());
    }
}
