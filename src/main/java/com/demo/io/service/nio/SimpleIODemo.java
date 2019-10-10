package com.demo.io.service.nio;

import java.nio.IntBuffer;

/**
 * Created by w景洋
 * on 2019/10/8
 */
public class SimpleIODemo {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i =0; i<buffer.capacity(); i++){
            buffer.put(i);
        }
        // 反转，指针回到头部
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
