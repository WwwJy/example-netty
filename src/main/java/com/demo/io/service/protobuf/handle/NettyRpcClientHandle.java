package com.demo.io.service.protobuf.handle;

import com.demo.io.service.protobuf.proto.MyKindMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * Created by w景洋
 * on 2019/9/20
 */
public class NettyRpcClientHandle extends SimpleChannelInboundHandler<MyKindMessage.MyMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        MyKindMessage.MyMessage m = null;
        if (randomInt == 0){
             m = MyKindMessage.MyMessage.newBuilder()
                    .setPerson(MyKindMessage.Person.newBuilder()
                            .setName("张三")
                            .setAge(26)
                            .setAddress("FJ")
                            .build())
                     .setDataType(MyKindMessage.MyMessage.DataType.PersonType)
                     .build();

        }else if(randomInt == 1){
            m = MyKindMessage.MyMessage.newBuilder()
                    .setDog(MyKindMessage.Dog.newBuilder()
                            .setName("旺财")
                            .setSex(1)
                            .build())
                    .setDataType(MyKindMessage.MyMessage.DataType.DogType)
                    .build();
        }else {
            m = MyKindMessage.MyMessage.newBuilder()
                    .setCat(MyKindMessage.Cat.newBuilder()
                            .setName("英短")
                            .setCity("FJ")
                            .build())
                    .setDataType(MyKindMessage.MyMessage.DataType.CatType)
                    .build();
        }
        ctx.channel().writeAndFlush(m);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyKindMessage.MyMessage myMessage) throws Exception {

    }
}
