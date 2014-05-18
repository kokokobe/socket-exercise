package com.liang.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 
* @ClassName: UDPClient
* @Description:(UDPclient发送数据)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014年2月14日
 */
public class UDPClient {    
    private String remoteHost="localhost";
    private int remotePort=8000;
    private DatagramSocket socket;
    public static void main(String[] args) throws SocketException {
        new UDPClient().talk();
    }
    public UDPClient() throws SocketException{
        socket=new DatagramSocket();
    }
    public void talk(){
        try {
            InetAddress remoteIp=InetAddress.getByName(remoteHost);
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while((msg=localReader.readLine())!=null){
                byte[] ouputData=msg.getBytes();
                DatagramPacket outputPacket=new DatagramPacket(ouputData,ouputData.length,remoteIp,remotePort);
                socket.send(outputPacket);
                DatagramPacket inputPacket=new DatagramPacket(new byte[512],512);
                //接收UDP服务器返回数据
                socket.receive(inputPacket);
                System.out.println(new String(inputPacket.getData(),0,inputPacket.getLength()));
                if(msg.equals("bye")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            socket.close();
        }
    }
}
