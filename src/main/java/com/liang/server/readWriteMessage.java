package com.liang.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class readWriteMessage {
    public static void receive(SelectionKey key) {
        // 获取与SelectionKey关联的附件
        Object attachment = key.attachment();
        ByteBuffer buffer = null;
        if (attachment != null) {
            buffer = (ByteBuffer) attachment;
        }
        // 获取与SelectionKey关联的SocketChannel
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 创建一个ByteBuffer，用于存放读到的数据
        ByteBuffer readBuffer = ByteBuffer.allocate(32);
        try {
            socketChannel.read(readBuffer);
            readBuffer.flip();
            // 将buffer的极限设为容量
            buffer.limit(buffer.capacity());
            // 把readBuff中的内容拷贝到buffer中，假定buffer的容量足够大，不会出现缓冲区溢出异常
            buffer.put(readBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void send(SelectionKey key) {
        try {
            // 获取与SelectionKey关联的ByteBuffer
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            // 获取与SelectionKey关联的SocketChannel
            SocketChannel socketChannel = (SocketChannel) key.channel();
            // 把极限设为当前位置，把位置设为0
            buffer.flip();
            // 按照UTF-8编码，把buffer中的字节转化为字符串
            String data = Charset.forName("UTF-8").decode(buffer).toString();
            // 如果没有读到一行数据就返回
            if (data.indexOf("\r\n") == -1) {
                return;
            }
            // 截取一行数据作为输出数据，难道有多行数据？应该是的,多行的话轮询下次输出，一次输出一行
            String outputData = data.substring(0, data.indexOf("\n") + 1);
            System.out.println(outputData);
            // 把输出数据按utf-8编码
            ByteBuffer outputBuffer = Charset.forName("UTF-8").encode("输出：" + outputData);
            while (outputBuffer.hasRemaining()) {
                // 输出的只是一行数据
                socketChannel.write(outputBuffer);
            }
            // 把输出outputData 按utf-8编码,转化为字节，放在ByteBuffer中,position=0,capacity=limit
            ByteBuffer temp = Charset.forName("UTF-8").encode(outputData);
            // 把buffer的位置设为temp的极限
            buffer.position(temp.limit());
            // 删除buffer中已经处理的数据
            buffer.compact();
            // 有一行接收到bye就使SelectionKey失效，并关闭socketChannel
            if (outputData.equals("bye\r\n")) {
                key.cancel();
                socketChannel.close();
                System.out.println("关闭与客户端连接");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
