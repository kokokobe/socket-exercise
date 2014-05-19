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
         * 输出缓冲区大小
         */
        s.setSendBufferSize(2048);
        /*
         * 接受缓冲区
         */
        s.setReceiveBufferSize(2048);
        /*
         * socket流等待时间超时就不接受数据
         */
        //s.setSoTimeout(5000);
        /*
         * 一个字节的紧急数据，接收方没法区别是否紧急
         */
        s.setOOBInline(true);
        /*
         * 设置网络服务类型高可靠性4表示高可靠性
         * 8表示高吞吐
         * 10表示最小延迟
         * 2表示低成本
         */
        s.setTrafficClass(0x04|0x10);
        //接受客服端信息流
        InputStream in=s.getInputStream();
        //向客户端发送流
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
                System.out.println("等待读超时");
                len=0;
            }
        } while (len!=-1);
        System.out.println(new String(buffer.toByteArray()));
    }
}
