package com.demo.io.service.grpc;

import com.demo.io.service.grpc.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by w景洋
 * on 2019/9/28
 */
public class GRpcClient {

    private final ManagedChannel channel;
    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;
    private final StudentServiceGrpc.StudentServiceStub stub;

    public GRpcClient(String host , int port) {
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        stub = StudentServiceGrpc.newStub(channel);
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
        System.out.println("--------------------------------");
    }

    public void getStudentByAge(int age){
        try {
            Iterator<StudentResponse> it = blockingStub.getStudentByAge(StudentRequest.newBuilder().setAge(age).build());
            while (it.hasNext()){
                StudentResponse res = it.next();
                System.out.println(res.getName() + "," + res.getAge() + "," + res.getCity());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("-------------------------------------");
    }

    public void getStudentsByAgeList(){
        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>(){
            @Override
            public void onNext(StudentResponseList studentResponseList) {
                studentResponseList.getStudentResponseList().forEach(studentResponse -> {
                    System.out.println(studentResponse.getName());
                    System.out.println(studentResponse.getAge());
                    System.out.println(studentResponse.getCity());
                    System.out.println("*************");
                });
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };

        StreamObserver<StudentRequest> studentRequestStreamObserver =
                stub.getStudentsWrapperByAges(studentResponseListStreamObserver);
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());
        studentRequestStreamObserver.onCompleted();
        System.out.println("-------------------------------------");
        // 阻塞线程以看到返回的异步结果
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getStudentBiTalk(){
        // 异步接收服务端的消息
        StreamObserver<StreamRequest> streamRequestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse streamResponse) {
                System.out.println(streamResponse.getResponseInfo());
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        });
        // 主动向服务端发送数据
        for (int i = 0 ; i<100; i++){
            streamRequestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() throws InterruptedException {
        // 阻塞5秒关闭
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GRpcClient client = new GRpcClient("127.0.0.1",8090);
        try {
//            client.greet("我擦");
//            client.getStudentByAge(20);
//            client.getStudentsByAgeList();
            client.getStudentBiTalk();
        }finally {
            client.shutdown();
        }
    }
}
