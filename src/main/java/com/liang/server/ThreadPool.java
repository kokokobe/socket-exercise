package com.liang.server;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup{
    //线程池是否关闭
    private boolean isClosed=false;
    //表示工作队列
    private LinkedList<Runnable> workQueue;
    //表示线程池id
    private static int threadPoolID;

    public ThreadPool(int poolSize) {
        super("ThreadPool-"+(threadPoolID++));
        setDaemon(true);
        //创建工作队列
        workQueue=new LinkedList<Runnable>();
        for(int i=0;i<poolSize;i++){
            new WorkThread().start();
        }
    }
    /**
     *
     * @Title: execute
     * @Description: TODO(向工作队列中加入一个新任务，由工作线程去执行)
     * @param task
     * @return 返回类型  void
     * @throws
     */
    public synchronized void execute(Runnable task){
        //线程池关闭
        if(isClosed){
            throw new IllegalStateException();
        }
        if(task!=null){
            workQueue.add(task);
            //唤醒正在getTask()方法中等待任务的工作线程
            notify();
        }
    }
    /**
     * @Title: getTask
     * @Description: TODO(从工作队列中取出一个任务，工作线程会调用此方法)
     * @throws InterruptedException
     * @return 返回类型  Runnable
     */
    protected synchronized Runnable getTask() throws InterruptedException{
        while(workQueue.size()==0){
            if(isClosed){
                return null;
            }
            //如果工作队列没有任务就等待 
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
     * @Description: TODO(等待工作线程把所有任务执行完)
     * @return 返回类型  void
     */
    public void join(){
        synchronized (this) {
            isClosed=true;
            //唤醒还在getTask()方法中等待任务的工作线程
            notifyAll();
        }
        Thread[] threads=new Thread[activeCount()];
        //enumerate()方法继承自ThreadGroup类，获得线程组中当前活着的所有工作线程
        int count=enumerate(threads);
        //等待所有工作线程结束
        for(int i=0;i<count;i++){
            try {
                //等待工作线程运行结束
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //工作线程
    private class WorkThread extends Thread{
        public WorkThread(){
            //加入到当前ThreadPool线程组中
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
                //如果getTask（）返回null或者线程执行getTask()时被中断，则结束此线程
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
