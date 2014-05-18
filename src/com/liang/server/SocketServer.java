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
 * @Description: TODO(使用非阻塞轮询方式作为socketServer)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013年12月5日
 */
public class SocketServer {
    // 服务器端口
    private final int port = 8000;
    private static ServerSocketChannel serverSocketChannel;
    // 线程池
    private static ExecutorService executorService;
    // 单个CPU时线程池中的工作线程数目
    private final int POOL_SIZE = 4;
    private Selector selector;
    private Object gate=new Object();
    public SocketServer() throws IOException {
        // 创建一个Selector对象
        selector = Selector.open();
        // 创建一个ServerSocketChannel
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        // 绑定服务器进程与端口
        serverSocketChannel.bind(new InetSocketAddress("localhost", port));
    }
    
    public void accept(){
        for(;;){
            try {
                SocketChannel socketChannel=serverSocketChannel.accept();
                System.out.println("接受客户端连接，来自："+socketChannel.socket().getInetAddress()+"--->"+socketChannel.socket().getPort());
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
            //空的同步块，其作用是等待等待监听端口socketChannel注册完读就绪事件，释放锁之后调用selector.select()，
            //否则就阻塞
            synchronized (gate) {
                
            }
            SelectionKey key=null;
            try {
                //已就绪事件个数
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
