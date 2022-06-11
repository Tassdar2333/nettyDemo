package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/5 21:18
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInpuStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInpuStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while(true){//循环读取

            //重置 清空
            byteBuffer.clear();

            int read = fileChannel01.read(byteBuffer);
            if(read == -1){
                //表示读取完毕
                break;
            }
            //别忘了反转
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);

        }
        //关闭相关流
        fileInpuStream.close();
        fileOutputStream.close();
    }
}
