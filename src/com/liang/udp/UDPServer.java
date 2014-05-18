package com.liang.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    private int port=8000;
    //UDP socket���ݱ�socket
    private DatagramSocket socket;
    public UDPServer() throws SocketException{
        //��һ�����صĹ̶��˿ڽ���
        socket=new DatagramSocket(port);
        System.out.println("�������Ѿ�����");
    }
    public String echo(String msg){
        return "echo:"+msg;
    }
    public void service(){
        while (true) {
            try {
                //UDP���ݱ�
                DatagramPacket packet=new DatagramPacket(new byte[512],512);
                //����û�ж���host��ַ�����Խ�������һ��Client�����ݱ�
                socket.receive(packet);
                String msg=new String(packet.getData(),0,packet.getLength());
                System.out.println(packet.getAddress()+":"+packet.getPort()+"--->"+msg);
                packet.setData(echo(msg).getBytes());
                //�ظ����ݱ�
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws SocketException {
        new UDPServer().service();
    }
}
