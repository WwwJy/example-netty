package com.demo.io.service.nio;

import java.nio.ByteBuffer;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class NioTest1 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0;i<buffer.capacity(); i++){
            buffer.put((byte) i);
        }
        buffer.position(2);
        buffer.limit(6);
        ByteBuffer sliceBuff = buffer.slice();

        for (int i = 0; i< sliceBuff.capacity(); i++){
            byte b = sliceBuff.get(i);
            b *= 2;
            sliceBuff.put(i,b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
