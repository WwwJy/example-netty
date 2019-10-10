package com.demo.io.service.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by w景洋
 * on 2019/10/10
 */
public class NioTest2 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest2.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        // 堆外内存映射，修改mmap也会作用到对外内存的数据。
        // 这里以一个文件的例子，将文件内容映射到mmap中，同时修改mmap又反作用到文件上。
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);

        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(3, (byte) 'c');

        randomAccessFile.close();
    }
}
