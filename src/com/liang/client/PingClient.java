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
 * @Description:(�������ͻ���)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013��12��10��
 */
public class PingClient {
    private Selector selector;
    // ����û��ύ������
    private LinkedList<Object> targets = new LinkedList<>();
    // ����Ѿ�����������Ҫ��ӡ������
    private LinkedList finishedTargets = new LinkedList<>();
    //���ڿ���Connector�߳�
    private boolean shutdown=false;
    public PingClient() throws IOException {
        selector = Selector.open();
        Connector connector = new Connector();
        Printer printer = new Printer();
        // ���������߳�
        connector.start();
        // ������ӡ�߳�
        printer.start();
        // ���߳̽����û��ӿ���̨�������������Ȼ���ύTarget
        receiveTarget();
    }
    public void addTarget(Target target) {
        // ��targets�����м���һ���������̻߳���ø÷���
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(target.address);
            target.channel = socketChannel;
            target.connectStart = System.currentTimeMillis();
            // ͬ��ֻ��һ���߳̿��Լ����������
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
        // ��finshedTargets���м���һ���������̺߳�Connector�̻߳���ø÷���
        synchronized (finishedTargets) {
            finishedTargets.notify();
            finishedTargets.add(target);
        }
    }

    public void printFinishedTargets() {
        // ��ӡfinishedTargets�����е�����Printer�̻߳���ø÷���
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
     * @Description:(ȡ������ע������socketChannel����Selectorע�����Ӿ����¼���Connector�̻߳���ô˷���)
     * @param
     * @return �������� void
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
     * @Description:(�������Ӿ����¼�)
     * @param
     * @return �������� void
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
    * @Description:(�����û��������������targets�����м����������̻߳���ø÷���)
    * @param    
    * @return ��������  void    
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
                    System.out.println("���ӹر�");
                    //ʹConnector�̴߳�Selector��selector()�������˳�
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
