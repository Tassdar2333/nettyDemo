package com.atguigu.nio;

import java.nio.IntBuffer;

/**
 * @version 1.0
 * @Auther wangchengyang
 * @Date 2022/5/2 13:59
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //举例说明Buffer 的使用（简单说明）
        //创建Buffer，大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

//        intBuffer.put(10);
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);

        for(int i = 0 ; i < intBuffer.capacity() ; i++){
            intBuffer.put(i * 2);
        }
        /**
         * public final Buffer flip() {
         *         limit = position;
         *         position = 0;
         *         mark = -1;
         *         return this;
         *     }
         */
        intBuffer.flip();
        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
