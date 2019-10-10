package com.demo.io.service.nio.file;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class NioFileTest1 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest1.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] message = "hello world !".getBytes();
        for (int i=0 ; i<message.length; i++){
            byteBuffer.put(message[i]);
        }
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
