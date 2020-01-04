package com.demo.io.service.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by w景洋
 * on 2019/12/4
 */
public class ByteBufTest0 {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
    }
}
