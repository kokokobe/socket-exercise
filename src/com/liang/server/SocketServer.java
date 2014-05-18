package com.liang.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * 
 * @ClassName: SocketServer
 * @Description: TODO(ʹ�÷�������ѯ��ʽ��ΪsocketServer)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013��12��5��
 */
public class SocketServer {
    // �������˿�
    private final int port = 8000;
    private static ServerSocketChannel serverSocketChannel;
    // �̳߳�
    private static ExecutorService executorService;
    // ����CPUʱ�̳߳��еĹ����߳���Ŀ
    private final int POOL_SIZE = 4;
    private Selector selector;
    private Object gate=new Object();
    public SocketServer() throws IOException {
        // ����һ��Selector����
        selector = Selector.open();
        // ����һ��ServerSocketChannel
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        // �󶨷�����������˿�
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
    }
    
    public void accept(){
        for(;;){
            try {
                SocketChannel socketChannel=serverSocketChannel.accept();
                System.out.println("���ܿͻ������ӣ����ԣ�"+socketChannel.socket().getInetAddress()+"--->"+socketChannel.socket().getPort());
                ByteBuffer buffer=ByteBuffer.allocate(1024);
                socketChannel.configureBlocking(false);
                synchronized (gate) {
                    selector.wakeup();
                    socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE,buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void service(){
        for(;;){
            //�յ�ͬ���飬�������ǵȴ��ȴ������˿�socketChannelע����������¼����ͷ���֮�����selector.select()��
            //���������
            synchronized (gate) {
                
            }
            SelectionKey key=null;
            try {
                //�Ѿ����¼�����
                int n=selector.select();
                if(n==0){
                    continue;
                }
                Set readyKeys=selector.selectedKeys();
                Iterator itr=readyKeys.iterator();
                while(itr.hasNext()){
                    key=(SelectionKey)itr.next();
                    itr.remove();
                    if(key.isReadable()){
                        readWriteMessage.receive(key);
                    }
                    if(key.isWritable()){
                        readWriteMessage.send(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if(key!=null){
                        key.cancel();
                        key.channel().close();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                
            }
        }
    }
    public static void main(String[] args) throws IOException {
        final SocketServer server=new SocketServer();
        Thread accept=new Thread(){
            public void run()   {
                server.accept();
            }
        };
        accept.start();
        server.service();
    }
}
