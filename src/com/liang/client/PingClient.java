package com.liang.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @ClassName: PingClient
 * @Description:(非阻塞客户端)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013年12月10日
 */
public class PingClient {
    private Selector selector;
    // 存放用户提交的任务
    private LinkedList<Object> targets = new LinkedList<>();
    // 存放已经完成任务的需要打印的任务
    private LinkedList finishedTargets = new LinkedList<>();
    //用于控制Connector线程
    private boolean shutdown=false;
    public PingClient() throws IOException {
        selector = Selector.open();
        Connector connector = new Connector();
        Printer printer = new Printer();
        // 启动连接线程
        connector.start();
        // 启动打印线程
        printer.start();
        // 主线程接收用户从控制台输入的主机名，然后提交Target
        receiveTarget();
    }
    public void addTarget(Target target) {
        // 向targets队列中加入一个任务，主线程会调用该方法
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(target.address);
            target.channel = socketChannel;
            target.connectStart = System.currentTimeMillis();
            // 同步只有一个线程可以加入任务队列
            synchronized (targets) {
                targets.add(target);
            }
            selector.wakeup();
        } catch (IOException e) {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            target.failure = e;
            addFinishedTarget(target);
        }
    }

    private void addFinishedTarget(Target target) {
        // 向finshedTargets队列加入一个任务，主线程和Connector线程会调用该方法
        synchronized (finishedTargets) {
            finishedTargets.notify();
            finishedTargets.add(target);
        }
    }

    public void printFinishedTargets() {
        // 打印finishedTargets队列中的任务，Printer线程会调用该方法
        try {
            for (;;) {
                Target target = null;
                synchronized (finishedTargets) {
                    while (finishedTargets.size() == 0) {
                        finishedTargets.wait();
                    }
                    target = (Target) finishedTargets.removeFirst();
                }
                target.show();
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * 
     * @Title: registerTargets
     * @Description:(取出队列注册链接socketChannel，向Selector注册连接就绪事件，Connector线程会调用此方法)
     * @param
     * @return 返回类型 void
     * @throws
     */
    public void registerTargets() {
        synchronized (targets) {
            while (targets.size() > 0) {
                Target target = (Target) targets.removeFirst();
                try {
                    target.channel.register(selector, SelectionKey.OP_CONNECT, target);
                } catch (IOException e) {
                    try {
                        target.channel.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    target.failure = e;
                    addFinishedTarget(target);
                }
            }
        }
    }

    /**
     * 
     * @Title: processSelectedKeys
     * @Description:(处理连接就绪事件)
     * @param
     * @return 返回类型 void
     * @throws
     */
    public void processSelectedKeys() {
        for (Iterator it = selector.selectedKeys().iterator(); it.hasNext();) {
            SelectionKey selectionKey = (SelectionKey) it.next();
            it.remove();
            Target target = (Target) selectionKey.attachment();
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            try {
                if(socketChannel.finishConnect()){
                    selectionKey.cancel();
                    target.connectFinish=System.currentTimeMillis();
                    socketChannel.close();
                    addFinishedTarget(target);
                }
            } catch (Exception e) {
                try {
                    if(socketChannel!=null){
                        socketChannel.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                target.failure=e;
                addFinishedTarget(target);
            }
        }
    }
    /**
     * 
    * @Title: receiveTarget
    * @Description:(接受用户输入的域名，向targets队列中加入任务，主线程会调用该方法)
    * @param    
    * @return 返回类型  void    
    * @throws
     */
    public void receiveTarget(){
        try {
            BufferedReader readBuffer=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while((msg=readBuffer.readLine())!=null){
                if(!msg.equals("bye")){
                    Target target=new Target(msg);
                    addTarget(target);
                }
                else{
                    shutdown=true;
                    System.out.println("连接关闭");
                    //使Connector线程从Selector的selector()方法中退出
                    selector.wakeup();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class Printer extends Thread{
        public Printer() {
            setDaemon(true);
        }
        public void run(){
            printFinishedTargets();
        }
    }
    public class Connector extends Thread{
        public void run(){
            while(!shutdown){
                try {
                    registerTargets();
                    if(selector.select()>0){
                        processSelectedKeys();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new PingClient();
    }
}
