package com.demo.io.service.nio.file;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class NioFileTest2 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("NioTest1.txt");
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            char c = (char) byteBuffer.get();
            System.out.println("Character : " + c);
        }
        fileInputStream.close();
    }
}
