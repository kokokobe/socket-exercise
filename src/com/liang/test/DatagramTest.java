package com.liang.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 
 * @ClassName: DatagramTest
 * @Description:(DatagramPacket UDP ���ݱ����Ա�����)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014��2��18��
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
     * @Description:(����UDP����)
     * @param byte[] bigData
     * @return �������� void
     * @throws
     */
    public void send(byte[] bigData) throws IOException {
        DatagramPacket packet = new DatagramPacket(bigData, 0, 512, InetAddress.getByName("localhost"), port);
        // �ѷ��͵��ֽ���
        int bytesSent = 0;
        // ���͵Ĵ���
        int count = 0;
        while (bytesSent < bigData.length) {
            sendSocket.send(packet);
            System.out.println("SendSocket--->��"+(++count)+"�η�����"+packet.getLength()+"���ֽ�");
            //getLength()����ʵ�ʵķ����ֽڳ���
            bytesSent+=packet.getLength();
            //δ���͵��ֽ���
            int remain=bigData.length-bytesSent;
            //�´η������ݳ���
            int length=(remain>512)? 512:remain;
            //�ı��־λ�ͷ��͵ĳ���
            packet.setData(bigData, bytesSent, length);
        }
    }
    public byte[] receive() throws IOException{
        byte[] bigData=new byte[MAX_LENGTH];
        DatagramPacket packet=new DatagramPacket(bigData, 0, MAX_LENGTH);
        //�Ѿ����յ��ֽ���
        int byteReceived=0;
        //��ʾ���յĴ���
        int count=0;
        long beginTime=System.currentTimeMillis();
        //������յ���bigData.length���ֽڣ����߳���5���ӣ��ͽ���ѭ��
        while((byteReceived<bigData.length)&&(System.currentTimeMillis()-beginTime<60000*5)){
            receiveSocket.receive(packet);
            System.out.println("receiveSocket--->��"+(++count)+"�ν��յ���"+packet.getLength()+"���ֽ�");
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
                //��ӡ��������
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
