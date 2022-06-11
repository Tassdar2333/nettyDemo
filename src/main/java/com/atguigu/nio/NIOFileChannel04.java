package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/5 21:42
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{ FileInputStream fileInputStream = new FileInputStream("d:\\a.jpg");
       FileOutputStream fileOutputStream =  new FileOutputStream("d:\\a2.jpg");
       FileChannel sourceCh = fileInputStream.getChannel();
       FileChannel destCh = fileOutputStream.getChannel();
       destCh.transferFrom(sourceCh,0,sourceCh.size());
       destCh.close();
       sourceCh.close();
       fileInputStream.close();
       fileOutputStream.close();
    }
}
