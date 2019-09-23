package com.demo.io.service.protobuf;

import com.demo.io.service.protobuf.proto.MyDataInfo;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by w景洋
 * on 2019/9/19
 */
public class App {
    /**
     *
     * @param args
     * @throws InvalidProtocolBufferException
     */
    public static void main(String[] args) throws InvalidProtocolBufferException {
        MyDataInfo.Person person = MyDataInfo.Person.newBuilder()
                .setName("WJY").setAge(26).setAddress("FJ").build();
        byte[] person2ByteArray = person.toByteArray();
        MyDataInfo.Person person2 = MyDataInfo.Person.parseFrom(person2ByteArray);
        System.out.println(person2.getName());
        System.out.println(person2.getAge());
        System.out.println(person2.getAddress());
    }
}
