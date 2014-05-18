package com.liang.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * 
 * @ClassName: EchoServer
 * @Description:(����ģʽDatagramChannel)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014��3��24��
 * @version 1.0
 * @since
 */
public class EchoServer {
    private int port = 8000;
    private DatagramChannel channel;
    private final int MAX_SIZE = 1024;

    public EchoServer() throws IOException {
        channel = DatagramChannel.open();
        DatagramSocket socket = channel.socket();
        SocketAddress localAddr = new InetSocketAddress(port);
        socket.bind(localAddr);
        System.out.println("����������");
    }

    public String echo(String msg) {
        return "echo:" + msg;
    }

    public void service() {
        ByteBuffer receiveBuffer=ByteBuffer.allocate(MAX_SIZE);
        while(true){
            try {
                receiveBuffer.clear();
                /*������������һ��EchoClient�����ݱ�*/
                InetSocketAddress client=(InetSocketAddress) channel.receive(receiveBuffer);
                receiveBuffer.flip();
                String msg=Charset.forName("UTF-8").decode(receiveBuffer).toString();
                System.out.println(client.getAddress()+":"+client.getPort()+"-->"+msg);
                /*���ͷ��˷���һ���ظ�*/
                channel.send(ByteBuffer.wrap(echo(msg).getBytes()), client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }
}
