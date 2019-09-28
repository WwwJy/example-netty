package com.demo.io.service.grpc;

import com.demo.io.service.grpc.proto.MyRequest;
import com.demo.io.service.grpc.proto.MyResponse;
import com.demo.io.service.grpc.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by w景洋
 * on 2019/9/28
 */
public class GRpcClient {

    private final ManagedChannel channel;
    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;

    public GRpcClient(String host , int port) {
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
    }

    public void greet(String name){
        MyRequest request = MyRequest.newBuilder()
                .setUsername(name)
                .build();
        MyResponse response = null;
        try {
             response = blockingStub.getRealNameByUsername(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(response.getRealName());
    }

    public void shutdown() throws InterruptedException {
        // 阻塞5秒关闭
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GRpcClient client = new GRpcClient("127.0.0.1",8090);
        try {
            client.greet("我擦");
        }finally {
            client.shutdown();
        }
    }
}
