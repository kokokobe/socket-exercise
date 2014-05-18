package com.liang.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @ClassName: EchoClient
 * @Description:(UDP DatagramChannel客户端)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年3月24日
 * @version
 * @since
 */
public class EchoClient {
    private DatagramChannel datagramChannel = null;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
    private Charset charset = Charset.forName("utf-8");
    private Selector selector;

    /* 默认采用7000端口来接发 */
    public EchoClient() throws IOException {
        this(7000);
    }

    public EchoClient(int port) throws IOException {
        datagramChannel = DatagramChannel.open();
        /* 远程端口也就是本地地址 */
        InetAddress iAd = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(iAd, port);
        /* 非阻塞模式 */
        datagramChannel.configureBlocking(false);
        /* 与本地端口绑定 */
        datagramChannel.socket().bind(isa);
        isa = new InetSocketAddress(iAd, 8000);
        /* 与远程地址连接 */
        datagramChannel.connect(isa);
        selector = Selector.open();
    }

    public void receiveFromUser() {
        try {
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while ((msg = localReader.readLine()) != null) {
                synchronized (sendBuffer) {
                    sendBuffer.put(encode(msg + "\r\n"));
                }
                if (msg.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void talk() throws IOException {
        datagramChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        while (selector.select() > 0) {
            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    key = it.next();
                    it.remove();
                    if (key.isReadable()) {
                        receive(key);
                    }
                    if (key.isWritable()) {
                        send(key);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        if (key != null) {
                            key.cancel();
                            key.channel().close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

            }
        }
    }

    private void receive(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.read(receiveBuffer);
        receiveBuffer.flip();
        String receiveData = decode(receiveBuffer);
        if (receiveData.indexOf("\n") == -1) {
            return;
        }
        String outputData=receiveData.substring(0,receiveData.indexOf("\n")+1);
        System.out.println("接收到数据："+outputData);
        if(outputData.equals("echo:bye\r\n")){
            key.cancel();
            datagramChannel.close();
            System.out.println("关闭与服务器的连接");
            selector.close();
            System.exit(0);
        }
        ByteBuffer temp=encode(outputData);
        receiveBuffer.position(temp.limit());
        receiveBuffer.compact();
    }

    private String decode(ByteBuffer buffer) {
        CharBuffer charBuffer=charset.decode(buffer);
        return charBuffer.toString();
    }
    private ByteBuffer encode(String data) {
        return charset.encode(data);
    }
    /**
     * 
    * @Title: send
    * @Description:(发送数据)
    * @author: BriLiang
    * @param key
    * @throws IOException
    * @since
     */
    private void send(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        synchronized (sendBuffer) {
            sendBuffer.flip();
            channel.write(sendBuffer);
            sendBuffer.compact();
        }
    }

    public static void main(String[] args) throws IOException {
        int port=7000;
        if(args.length>0){
            port=Integer.parseInt(args[0]);
        }
        final EchoClient client=new EchoClient(port);
        /*等待用户输入线程*/
        Thread receiver=new Thread(){
            public void run(){
                client.receiveFromUser();
            }
        };
        receiver.start();
        client.talk();
    }
}
