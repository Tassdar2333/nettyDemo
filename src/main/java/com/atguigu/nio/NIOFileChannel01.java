package com.atguigu.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/4 20:36
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "hello,尚硅谷";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
        //通过流来获取通道的实现类
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        //buffer切换为写
        buffer.flip();
        //将buffer中的数据写入到channel
        fileChannel.write(buffer);

        fileOutputStream.close();


    }
}
