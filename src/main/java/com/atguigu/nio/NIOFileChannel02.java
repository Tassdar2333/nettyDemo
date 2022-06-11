package com.atguigu.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/4 21:12
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        File file = new File("d:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        fileChannel.read(buffer);
        System.out.println(new String(buffer.array()));
        fileInputStream.close();

    }
}