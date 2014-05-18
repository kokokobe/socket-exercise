package com.liang.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class readWriteMessage {
    public static void receive(SelectionKey key) {
        // ��ȡ��SelectionKey�����ĸ���
        Object attachment = key.attachment();
        ByteBuffer buffer = null;
        if (attachment != null) {
            buffer = (ByteBuffer) attachment;
        }
        // ��ȡ��SelectionKey������SocketChannel
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // ����һ��ByteBuffer�����ڴ�Ŷ���������
        ByteBuffer readBuffer = ByteBuffer.allocate(32);
        try {
            socketChannel.read(readBuffer);
            readBuffer.flip();
            // ��buffer�ļ�����Ϊ����
            buffer.limit(buffer.capacity());
            // ��readBuff�е����ݿ�����buffer�У��ٶ�buffer�������㹻�󣬲�����ֻ���������쳣
            buffer.put(readBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void send(SelectionKey key) {
        try {
            // ��ȡ��SelectionKey������ByteBuffer
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            // ��ȡ��SelectionKey������SocketChannel
            SocketChannel socketChannel = (SocketChannel) key.channel();
            // �Ѽ�����Ϊ��ǰλ�ã���λ����Ϊ0
            buffer.flip();
            // ����UTF-8���룬��buffer�е��ֽ�ת��Ϊ�ַ���
            String data = Charset.forName("UTF-8").decode(buffer).toString();
            // ���û�ж���һ�����ݾͷ���
            if (data.indexOf("\r\n") == -1) {
                return;
            }
            // ��ȡһ��������Ϊ������ݣ��ѵ��ж������ݣ�Ӧ���ǵ�,���еĻ���ѯ�´������һ�����һ��
            String outputData = data.substring(0, data.indexOf("\n") + 1);
            System.out.println(outputData);
            // ��������ݰ�utf-8����
            ByteBuffer outputBuffer = Charset.forName("UTF-8").encode("�����" + outputData);
            while (outputBuffer.hasRemaining()) {
                // �����ֻ��һ������
                socketChannel.write(outputBuffer);
            }
            // �����outputData ��utf-8����,ת��Ϊ�ֽڣ�����ByteBuffer��,position=0,capacity=limit
            ByteBuffer temp = Charset.forName("UTF-8").encode(outputData);
            // ��buffer��λ����Ϊtemp�ļ���
            buffer.position(temp.limit());
            // ɾ��buffer���Ѿ����������
            buffer.compact();
            // ��һ�н��յ�bye��ʹSelectionKeyʧЧ�����ر�socketChannel
            if (outputData.equals("bye\r\n")) {
                key.cancel();
                socketChannel.close();
                System.out.println("�ر���ͻ�������");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
