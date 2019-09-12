package com.demo.io.service.http;

import com.demo.io.service.http.handle.NettyHttpServiceHandle;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by w景洋
 * on 2019/9/11
 */
public class HttpServiceChannel extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        // 管道，里面有多个channelHandle相当于拦截器,把每个请求分配到业务逻辑channel去
        ChannelPipeline pipeline = ch.pipeline();
        // http的一个重要组件
        // HttpServerCodec,NettyHttpServiceHandle 不能是单例的，每次必须生成出新的对象
        // httpServiceCodec -> 编解码工具，里面包含了 HttpRequestDecoder 和 HttpResponseEncoder
        // httpServiceHandle -> 请求和业务相关的实现
        pipeline.addLast("httpServiceCodec",new HttpServerCodec());
        pipeline.addLast("httpServiceHandle",new NettyHttpServiceHandle());
    }
}
