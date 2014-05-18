package com.liang.server;

public class ThreadPoolTest {
    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("用法：java ThreadPoolTest num Tasks poolSize");
            System.out.println(" num Tasks -integer :任务数目");
            System.out.println("num Threads-integer :线程池中的线程数目");
            return;
        }
        int numTasks=Integer.valueOf(args[0]);
        int poolSize=Integer.valueOf(args[1]);
        //创建线程池
        ThreadPool threadPool=new ThreadPool(poolSize);
        //运行任务
        for(int i=0;i<numTasks;i++){
            threadPool.execute(createTask(i));
            //等待工作线程完成所有任务
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
