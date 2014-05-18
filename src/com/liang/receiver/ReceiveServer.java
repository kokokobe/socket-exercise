package com.liang.receiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ReceiveServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(8000);
        Socket s=serverSocket.accept();
        /*
         * �����������С
         */
        s.setSendBufferSize(2048);
        /*
         * ���ܻ�����
         */
        s.setReceiveBufferSize(2048);
        /*
         * socket���ȴ�ʱ�䳬ʱ�Ͳ���������
         */
        //s.setSoTimeout(5000);
        /*
         * һ���ֽڵĽ������ݣ����շ�û�������Ƿ����
         */
        s.setOOBInline(true);
        /*
         * ��������������͸߿ɿ���4��ʾ�߿ɿ���
         * 8��ʾ������
         * 10��ʾ��С�ӳ�
         * 2��ʾ�ͳɱ�
         */
        s.setTrafficClass(0x04|0x10);
        //���ܿͷ�����Ϣ��
        InputStream in=s.getInputStream();
        //��ͻ��˷�����
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        byte[] buff=new byte[1024];
        int len=-1;
        do {
            try {
                len=in.read(buff);
                if(len!=-1){
                   buffer.write(buff,0,len);
                }
            } catch (SocketTimeoutException e) {
                System.out.println("�ȴ�����ʱ");
                len=0;
            }
        } while (len!=-1);
        System.out.println(new String(buffer.toByteArray()));
    }
}
