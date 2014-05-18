package com.liang.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ReceiveChannel {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int ENOUGH_SIZE = 1024;
        final int SMALL_SIZE = 4;
        // ���������������ģʽ
        boolean isBlocked = true;
        // ��ʾ��������С
        int size = ENOUGH_SIZE;
        if (args.length > 0) {
            // ��ȡ������
            int opt = Integer.parseInt(args[0]);
            switch (opt) {
            case 1:
                isBlocked = true;
                size = ENOUGH_SIZE;
                break;
            case 2:
                isBlocked = true;
                size = SMALL_SIZE;
                break;
            case 3:
                isBlocked = false;
                size = ENOUGH_SIZE;
                break;
            case 4:
                isBlocked = false;
                size = SMALL_SIZE;
                break;
                
            }
        }
        DatagramChannel channel=DatagramChannel.open();
        channel.configureBlocking(isBlocked);
        //������ݻ�����
        ByteBuffer buffer=ByteBuffer.allocate(size);
        DatagramSocket socket=channel.socket();
        SocketAddress localAddr=new InetSocketAddress(8000);
        socket.bind(localAddr);
        while(true){
            System.out.println("��ʼ�������ݱ�");
            SocketAddress remoteAddr=channel.receive(buffer);
            if(remoteAddr==null){
                System.out.println("û�н��յ����ݱ�");
            }
            else{
                buffer.flip();
                String receiveData=new String(buffer.array(),"utf-8");
                System.out.println(receiveData);
                System.out.println("���յ������ݱ���СΪ��"+buffer.remaining()+"���յ����ݣ�"+receiveData);
            }
            Thread.sleep(500);
        }
        
    }
}
