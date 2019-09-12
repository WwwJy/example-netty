package com.demo.io.service.websocket;

import com.demo.io.service.websocket.handle.NettyTextWebSocketHandle;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by w景洋
 * on 2019/9/12
 */
public class WebSocketServiceChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        // 块写的handle
        pipeline.addLast(new ChunkedWriteHandler());
        // 把报文段聚合成完整的报文，报文最大长度8192
        pipeline.addLast(new HttpObjectAggregator(8192));
        // /ws 为路由地址
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new NettyTextWebSocketHandle());
    }
}
