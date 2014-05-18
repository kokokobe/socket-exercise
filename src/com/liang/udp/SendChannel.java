package com.liang.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SendChannel {
    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramChannel channel=DatagramChannel.open();
        DatagramSocket socket=channel.socket();
        SocketAddress localAddr=new InetSocketAddress(7000);
        SocketAddress remoteAddr=new InetSocketAddress(InetAddress.getByName("localhost"), 8000);
        socket.bind(localAddr);
        while(true){
            ByteBuffer buffer=ByteBuffer.allocate(1024);
            buffer.clear();
            System.out.println("��������ʣ���ֽ�Ϊ��"+buffer.remaining());
            buffer.put("send message".getBytes());
            buffer.flip();
            int n=channel.send(buffer,remoteAddr);
            System.out.println("���͵��ֽ���Ϊ"+n);
            Thread.sleep(500);
        }
    }
    
}
