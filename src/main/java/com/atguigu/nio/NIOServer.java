package com.atguigu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/16 21:40
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = java.nio.channels.ServerSocketChannel.open();
        //创建一个选择器对象
        Selector selector = java.nio.channels.Selector.open();
        //绑定端口监听 666
        serverSocketChannel.bind(new InetSocketAddress(6666)).accept();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把ServerSocketChannel也注册到Selector 关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            //等待1秒
            if(selector.select(1000) == 0 ){//没有事件发生
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //如果返回值大于0 就获取到相关的集合
            //关注事件的集合
            //通过集合可以反向获取通道
            //这里的selector.selectedKeys只代表有事件发生的key而不是此时selector中有多少key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()){
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
                //根据key对应通道发生的事件做相应的处理
                if(key.isAcceptable()){
                    //为客户端生成一个SocektChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println("客户端连接成功 生成一个SocketChannel:" + socketChannel.hashCode());
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //将socketChannel注册 关注事件为读 给该SocketChannel关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){//事件OP_READ
                    //通过key反向获取chnnel
                    SocketChannel channel = (SocketChannel)key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("from客户端" + new String(buffer.array()));
                }
                //手动从集合中移除当前的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }

    }
}
