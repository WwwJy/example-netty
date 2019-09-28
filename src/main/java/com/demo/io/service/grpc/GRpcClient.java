package com.demo.io.service.grpc;

import com.demo.io.service.grpc.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

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

        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentsWrapperByAges(studentResponseListStreamObserver);

        System.out.println("-------------------------------------");
    }

    public void shutdown() throws InterruptedException {
        // 阻塞5秒关闭
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GRpcClient client = new GRpcClient("127.0.0.1",8090);
        try {
            client.greet("我擦");
            client.getStudentByAge(20);
        }finally {
            client.shutdown();
        }
    }
}
