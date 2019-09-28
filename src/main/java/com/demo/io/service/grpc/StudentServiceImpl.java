package com.demo.io.service.grpc;

import com.demo.io.service.grpc.proto.MyRequest;
import com.demo.io.service.grpc.proto.MyResponse;
import com.demo.io.service.grpc.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by w景洋
 * on 2019/9/28
 */
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
}
