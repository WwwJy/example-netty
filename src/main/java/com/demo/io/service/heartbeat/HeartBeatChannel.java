package com.demo.io.service.heartbeat;

import com.demo.io.service.heartbeat.handle.HeartBeatHandle;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by w景洋
 * on 2019/9/12
 */
public class HeartBeatChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 读空闲5秒 写空闲7秒 读写空闲10秒
        pipeline.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatHandle());
    }
}
