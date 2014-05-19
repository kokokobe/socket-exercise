package com.liang.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    private int port=8000;
    //UDP socket数据报socket
    private DatagramSocket socket;
    public UDPServer() throws SocketException{
        //与一个本地的固定端口接收
        socket=new DatagramSocket(port);
        System.out.println("服务器已经启动");
    }
    public String echo(String msg){
        return "echo:"+msg;
    }
    public void service(){
        while (true) {
            try {
                //UDP数据报
                DatagramPacket packet=new DatagramPacket(new byte[512],512);
                //由于没有定义host地址，所以接收任意一个Client的数据报
                socket.receive(packet);
                String msg=new String(packet.getData(),0,packet.getLength());
                System.out.println(packet.getAddress()+":"+packet.getPort()+"--->"+msg);
                packet.setData(echo(msg).getBytes());
                //回复数据报
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
