package com.demo.io.service.rpc.handle;

import com.demo.io.service.rpc.proto.MyDataInfo;
import com.demo.io.service.rpc.proto.MyKindMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by w景洋
 * on 2019/9/20
 */
public class NettyRpcServiceHandle extends SimpleChannelInboundHandler<MyKindMessage.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyKindMessage.MyMessage myMessage) throws Exception {
        String str = "";
        switch (myMessage.getDataType().getNumber()){
            case 1:
                 str = myMessage.getPerson().getName() + myMessage.getPerson().getAge() + myMessage.getPerson().getAddress();
                break;
            case 2:
                str = myMessage.getDog().getName() + myMessage.getDog().getSex();
                break;
            case 3:
                str = myMessage.getCat().getName() + myMessage.getCat().getCity();
                break;
        }
        System.out.println(str);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
