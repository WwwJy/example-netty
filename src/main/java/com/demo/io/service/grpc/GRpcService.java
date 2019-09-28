package com.demo.io.service.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created by w景洋
 * on 2019/9/28
 */
public class GRpcService {

    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(8090)
                .addService(new StudentServiceImpl())
                .build()
                .start();
        System.out.println("我开始跑了.");
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("关闭jvm");
            GRpcService.this.stop();
        }));
    }

    private void stop(){
        if (null != server){
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (null != server){
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GRpcService service = new GRpcService();
        service.start();
        service.blockUntilShutdown();
    }
}
