package com.atguigu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/18 21:41
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务端的ip 和 端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while(!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }

        //如果连接成功，发送数据
        String str = "hello,netty";
        //根据字节大小自动创建相应字节大小的buffer（不用指定大小）
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据 将buffer数据写入channel
        socketChannel.write(buffer);
        System.in.read();

    }
}
