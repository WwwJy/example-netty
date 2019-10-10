package com.demo.io.service.nio.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class NioFileTest3 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("NioInputTest3.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("NioOutPutTest3.txt");
        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        while (true){
            buffer.clear(); // 如果注释掉这行代码代码会如何呢。
            int read = inputChannel.read(buffer);
            System.out.println("read: " + read);
            if (read == -1){
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
