package com.atguigu.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/6/4 21:10
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 6667;

    //构造器 初始化工作
    public GroupChatServer(){

        try{
            //得到选择器
            selector = Selector.open();
            //初始化ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将该channel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    //监听
    public void listen(){

        try{

            while(true){
                int count = selector.select();
                if(count > 0){//有事件要处理
                    //遍历的到此时有时间的selectKey
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        //去除selectionKey
                        SelectionKey key = iterator.next();

                        //判断监听事件 acceptable 就是说有连接或者直白点 有数据要来 就需要去读
                        //下面就是会创建一个读的通道并注册到selector中
                        if(key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector,SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                        }

                        if(key.isReadable()){//通道发生read事件，即通道是可读的状态
                            //处理读（专门写方法）
                            readData(key);
                        }

                        //当前的key删除，防止重复处理
                        //手动从集合中移动当前的selectionKey,防止重复操作
                        iterator.remove();

                    }

                }else{
                    System.out.println("等待....");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * 读取客户端消息
     */
    private void readData(SelectionKey key){
        //定义一个SocketChannel
        SocketChannel channel = null;

        try{
            //取到关联的channel
            channel = (SocketChannel)key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //根据count的值做处理
            if(count > 0){
                //把缓存区的数据转化为字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端：" + msg);
                //向其他客户端转发消息 专门写一个方法来处理
                sendInfoToOtherClients(msg,channel);
            }
        }catch(IOException e){
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其他客户
     *
     */
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中");
        //遍历
        for (SelectionKey key : selector.keys()) {
            //通过key 取出对应的channel
            Channel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof SocketChannel && targetChannel != self){ //targetChannel instanceof SocketChannel &&

                //转型
                SocketChannel dest = (SocketChannel)targetChannel;

                //将msg存储在buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }


    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
