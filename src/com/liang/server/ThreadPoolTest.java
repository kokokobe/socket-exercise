package com.liang.server;

public class ThreadPoolTest {
    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("�÷���java ThreadPoolTest num Tasks poolSize");
            System.out.println(" num Tasks -integer :������Ŀ");
            System.out.println("num Threads-integer :�̳߳��е��߳���Ŀ");
            return;
        }
        int numTasks=Integer.valueOf(args[0]);
        int poolSize=Integer.valueOf(args[1]);
        //�����̳߳�
        ThreadPool threadPool=new ThreadPool(poolSize);
        //��������
        for(int i=0;i<numTasks;i++){
            threadPool.execute(createTask(i));
            //�ȴ������߳������������
            threadPool.join();
        }
    }
    private static Runnable createTask(final int taskID){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Task"+taskID+":start");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Task"+taskID+":end");
                }
            }
        };
    }
}
