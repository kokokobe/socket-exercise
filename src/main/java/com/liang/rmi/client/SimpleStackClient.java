package com.liang.rmi.client;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.service.Stack;

public class SimpleStackClient {
    public static void main(String[] args) {
        String rmiUrl = "rmi://localhost:1099/";
        try {
            Context namingContext = new InitialContext();
            Stack stack = (Stack) namingContext.lookup(rmiUrl + "MyStack");
            Producer producer1 = SimpleStackClient.getProducer(stack, "producer1");
            Producer producer2 = SimpleStackClient.getProducer(stack, "producer2");
            Consumer consumer = SimpleStackClient.getConsumer(stack, "consumer");
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * @Title: getProducer
     * @Description:(获得内部类实例)
     * @author: BriLiang
     * @param stack
     * @param name
     * @return Producer
     * @since
     */
    public static Producer getProducer(Stack stack, String name) {
        SimpleStackClient outer=new SimpleStackClient();
        return outer.new Producer(stack, name);
    }
    public static Consumer getConsumer(Stack stack, String name) {
        SimpleStackClient outer=new SimpleStackClient();
        return outer.new Consumer(stack, name);
    }
    /**
     *
     * @ClassName: Producer
     * @Description:(生产者线程)
     * @author BriLiang(liangwen.liang@vipshop.com)
     * @date 2014年4月2日
     * @version SimpleStackClient
     * @since
     */
    class Producer extends Thread {
        private Stack theStack;

        public Producer(Stack stack, String name) {
            super(name);
            this.theStack = stack;
            start();
        }

        public void run() {
            String goods;
            try {
                for (;;) {
                    synchronized (theStack) {
                        goods = "goods" + ((theStack.getPoint()) + 1);
                        theStack.push(goods);
                    }
                    System.out.println(getName() + " :push " + goods + " to " + theStack.getName());
                    Thread.sleep(500);
                }
            } catch (RemoteException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    class Consumer extends Thread{
        private Stack theStack;
        public Consumer(Stack stack, String name) {
            super(name);
            theStack=stack;
            start();
        }
        public void run(){
            String goods;
            try {
                for(;;){
                    goods=theStack.pop();
                    System.out.println(getName()+" :pop "+goods+" from "+theStack.getName());
                    Thread.sleep(400);
                }
            } catch (RemoteException | InterruptedException e) {

            }
        }
    }
}
