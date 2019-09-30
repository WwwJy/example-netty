package com.demo.io.service.grpc;

import com.demo.io.service.grpc.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("from client: " + request.getUsername());
        MyResponse res = MyResponse.newBuilder()
                .setRealName(request.getUsername())
                .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("from client : " + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("福建").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(20).setCity("广东").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(20).setCity("江苏").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("赵六").setAge(20).setCity("浙江").build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            List<StudentResponse> list = new ArrayList<>();
            @Override
            public void onNext(StudentRequest studentRequest) {
                StudentResponse rs = StudentResponse.newBuilder()
                        .setName("张三")
                        .setAge(studentRequest.getAge())
                        .setCity("上海")
                        .build();
                list.add(rs);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponseList sr = StudentResponseList.newBuilder()
                        .addAllStudentResponse(list)
                        .build();
                responseObserver.onNext(sr);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest streamRequest) {
                System.out.println("from client :" + streamRequest.getRequestInfo());
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
