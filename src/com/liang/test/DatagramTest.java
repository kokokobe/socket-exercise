package com.liang.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 
 * @ClassName: DatagramTest
 * @Description:(DatagramPacket UDP 数据报可以被重用)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年2月18日
 */
public class DatagramTest {
    private int port = 8000;
    private DatagramSocket sendSocket;
    private DatagramSocket receiveSocket;
    private static final int MAX_LENGTH = 3584;

    public DatagramTest() throws SocketException {
        sendSocket = new DatagramSocket();
        receiveSocket = new DatagramSocket(port);
        receiver.start();
        sender.start();
    }

    /**@Title: send
     * @throws IOException
     * @Description:(发送UDP数据)
     * @param byte[] bigData
     * @return 返回类型 void
     * @throws
     */
    public void send(byte[] bigData) throws IOException {
        DatagramPacket packet = new DatagramPacket(bigData, 0, 512, InetAddress.getByName("localhost"), port);
        // 已发送的字节数
        int bytesSent = 0;
        // 发送的次数
        int count = 0;
        while (bytesSent < bigData.length) {
            sendSocket.send(packet);
            System.out.println("SendSocket--->第"+(++count)+"次发送了"+packet.getLength()+"个字节");
            //getLength()返回实际的发送字节长度
            bytesSent+=packet.getLength();
            //未发送的字节数
            int remain=bigData.length-bytesSent;
            //下次发送数据长度
            int length=(remain>512)? 512:remain;
            //改变标志位和发送的长度
            packet.setData(bigData, bytesSent, length);
        }
    }
    public byte[] receive() throws IOException{
        byte[] bigData=new byte[MAX_LENGTH];
        DatagramPacket packet=new DatagramPacket(bigData, 0, MAX_LENGTH);
        //已经接收的字节数
        int byteReceived=0;
        //表示接收的次数
        int count=0;
        long beginTime=System.currentTimeMillis();
        //如果接收到了bigData.length的字节，或者超过5分钟，就结束循环
        while((byteReceived<bigData.length)&&(System.currentTimeMillis()-beginTime<60000*5)){
            receiveSocket.receive(packet);
            System.out.println("receiveSocket--->第"+(++count)+"次接收到了"+packet.getLength()+"个字节");
            byteReceived+=packet.getLength();
            packet.setData(bigData, byteReceived, MAX_LENGTH-byteReceived);
        }
        return packet.getData();
    }
    public Thread sender=new Thread(new Runnable() {
        @Override
        public void run() {
            long[] longArray=new long[MAX_LENGTH/8];
            for(int i=0;i<longArray.length;i++){
                longArray[i]=i+1;
            }
            try {
                send(ArrayConvertUtils.longToByte(longArray));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    public Thread receiver=new Thread(new Runnable() {
        
        @Override
        public void run() {
            try {
                long[] longArray=ArrayConvertUtils.byteToLong(receive());
                //打印接收数据
                for(int i=0;i<longArray.length;i++){
                    if(i%100==0){
                        System.out.println();
                        System.out.println(longArray[i]+"  ");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    public static void main(String[] args) throws SocketException {
        DatagramTest datagramTest=new DatagramTest();
    }
}
