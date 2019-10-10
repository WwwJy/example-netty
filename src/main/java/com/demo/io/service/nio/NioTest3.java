package com.demo.io.service.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by w景洋
 * on 2019/10/10
 */
public class NioTest3 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest2.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        // 从位置0到位置5进行锁定，锁类型为共享锁(并发读)，false表示排他锁
        FileLock lock = fileChannel.lock(0,5,true);

        System.out.println("valid : " + lock.isValid());
        System.out.println("share :" + lock.isShared());

        lock.release();
        randomAccessFile.close();
    }
}
