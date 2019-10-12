package com.demo.io.service.zerocopy;


import java.io.*;
import java.net.Socket;

/**
 * Created by w景洋
 * on 2019/10/12
 */
public class OldClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8090);
        String fileName = "";
        InputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer)) >= 0){
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送的总字节数: " + total + ", 耗时: "+(System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        inputStream.close();
        socket.close();
    }
}
