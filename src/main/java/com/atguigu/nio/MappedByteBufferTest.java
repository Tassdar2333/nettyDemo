package com.atguigu.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/13 21:45
 * 说明
 *MappedByteBuffer 可以让文件在内存(堆外内存）中修改 操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");
        //获取对应的文件通道
        FileChannel fileChannel = randomAccessFile.getChannel();
        /**
         * 参数1 使用的是读写模式
         * 参数2 可以直接修改的起始位置
         * 参数3 映射到内存的大小 即 将1.txt的多少个字节映射到内存
         * 可以直接修改的范围1就是0~5
         */
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);
        map.put(0,(byte)'H');
        map.put(3,(byte)'9');
        randomAccessFile.close();
        System.out.println("修改成功~");
    }
}
