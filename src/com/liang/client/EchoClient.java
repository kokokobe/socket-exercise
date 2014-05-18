package com.liang.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @ClassName:EchoClient
 * @Description:(������ģʽ��socketChannel�ͻ���)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013��12��9��
 */
public class EchoClient {
    // �ͻ���channel
    private SocketChannel socketChannel = null;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
    private Charset charset = Charset.forName("UTF-8");
    private Selector selector;

    public EchoClient() throws IOException {
        socketChannel = SocketChannel.open();
        // ��������ַ
        InetAddress serverAddress = InetAddress.getByName("localhost");
        InetSocketAddress socketAddress = new InetSocketAddress(serverAddress, 8000);
        // ���÷��������ӷ�����
        socketChannel.connect(socketAddress);
        // ������ģʽ
        socketChannel.configureBlocking(false);
        System.out.println("����������ӳɹ�");
        selector=Selector.open();
    }
    public static void main(String[] args) throws IOException {
        final EchoClient client=new EchoClient();
        Thread receiver=new Thread(){
            public void run(){
                client.receiveFromUser();
            }
        };
        receiver.start();
        client.talk();
    }
    /**
     * 
    * @Title: receiveFromUser
    * @Description:(���մ��û�����̨��������)
    * @param    
    * @return ��������  void    
    * @throws
     */
    public void receiveFromUser(){
        try {
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while((msg=localReader.readLine())!=null){
                synchronized (sendBuffer) {
                    sendBuffer.put(encode(msg+"\r\n"));
                }
                if(msg.equals("bye")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
    * @Title: talk
    * @Description: TODO(�����������ͽ�������)
    * @param    
    * @return ��������  void    
    * @throws
     */
    public void talk(){
        SelectionKey key=null;
        try {
            socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
            while(selector.select()>0){
                Set<SelectionKey> readKeys=selector.selectedKeys();
                Iterator<SelectionKey> itr=readKeys.iterator();
                while(itr.hasNext()){
                    key=itr.next();
                    itr.remove();
                    if(key.isReadable()){
                        receive(key);
                    }
                    if(key.isWritable()){
                        send(key);
                    }
                }
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if(key!=null){
                    key.cancel();
                    key.channel().close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    private void send(SelectionKey key) throws IOException {
        //����sendBuffer�е�����
        SocketChannel socketChannel=(SocketChannel)key.channel();
        synchronized (sendBuffer) {
            sendBuffer.flip();
            socketChannel.write(sendBuffer);
            //����ѷ��͵Ļ���
            sendBuffer.compact();
        }
    }
    private void receive(SelectionKey key) throws IOException {
        //����SocketServer���͵����ݣ������ŵ�receiveBuffer��
        //���receiveBuffer��һ�����ݣ��ʹ�ӡ�������ݣ�Ȼ���receiveBuffer��ɾ��
        SocketChannel socketChannel=(SocketChannel)key.channel();
        socketChannel.read(receiveBuffer);
        //limit �ǽ������ݵĳ���
        receiveBuffer.flip();
        String receiveMsg=decode(receiveBuffer);
        if(receiveMsg.indexOf("\n")==-1){
            return; 
        }
        String outputData=receiveMsg.substring(0,receiveMsg.indexOf("\n")+1);
        System.out.println(outputData);
        if(outputData.equals("�����echo:bye\r\n")){
            key.cancel();
            key.channel().close();
            System.out.println("�ر������������");
            selector.close();
            System.exit(0);
        }
        ByteBuffer temp=encode(outputData);
        //position���ѷ������ݵĳ���
        receiveBuffer.position(temp.limit());
        receiveBuffer.compact();
    }
    private String decode(ByteBuffer receiveBuffer) {
        return charset.decode(receiveBuffer).toString();
    }
    private ByteBuffer encode(String encode) {
        return charset.encode(encode);
    }
}
