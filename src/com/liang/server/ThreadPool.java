package com.liang.server;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup{
    //�̳߳��Ƿ�ر�
    private boolean isClosed=false;
    //��ʾ��������
    private LinkedList<Runnable> workQueue;
    //��ʾ�̳߳�id
    private static int threadPoolID;

    public ThreadPool(int poolSize) {
        super("ThreadPool-"+(threadPoolID++));
        setDaemon(true);
        //������������
        workQueue=new LinkedList<Runnable>();
        for(int i=0;i<poolSize;i++){
            new WorkThread().start();
        }
    }
    /**
     * 
    * @Title: execute
    * @Description: TODO(���������м���һ���������ɹ����߳�ȥִ��)
    * @param task   
    * @return ��������  void    
    * @throws
     */
    public synchronized void execute(Runnable task){
        //�̳߳عر�
        if(isClosed){
            throw new IllegalStateException();
        }
        if(task!=null){
            workQueue.add(task);
            //��������getTask()�����еȴ�����Ĺ����߳�
            notify();
        }
    }
    /**
    * @Title: getTask
    * @Description: TODO(�ӹ���������ȡ��һ�����񣬹����̻߳���ô˷���)
    * @throws InterruptedException   
    * @return ��������  Runnable    
     */
    protected synchronized Runnable getTask() throws InterruptedException{
        while(workQueue.size()==0){
            if(isClosed){
                return null;
            }
            //�����������û������͵ȴ� 
            wait();
        }
        return workQueue.removeFirst();
    }
    public synchronized void close(){
        if(!isClosed){
            isClosed=true;
            workQueue.clear();
            interrupt();
        }
    }
    /**
     * 
    * @Title: join
    * @Description: TODO(�ȴ������̰߳���������ִ����)
    * @return ��������  void    
     */
    public void join(){
        synchronized (this) {
            isClosed=true;
            //���ѻ���getTask()�����еȴ�����Ĺ����߳�
            notifyAll();
        }
        Thread[] threads=new Thread[activeCount()];
        //enumerate()�����̳���ThreadGroup�࣬����߳����е�ǰ���ŵ����й����߳�
        int count=enumerate(threads);
        //�ȴ����й����߳̽���
        for(int i=0;i<count;i++){
            try {
                //�ȴ������߳����н���
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //�����߳�
    private class WorkThread extends Thread{
        public WorkThread(){
            //���뵽��ǰThreadPool�߳�����
            super(ThreadPool.this,"WorkThread-"+(threadPoolID++));
        }
        public void run(){
            while(!isInterrupted()){
                Runnable task=null;
                try {
                    task=getTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //���getTask��������null�����߳�ִ��getTask()ʱ���жϣ���������߳�
                if(task==null){
                    return;
                }
                try {
                    task.run();  
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
    
    
}
