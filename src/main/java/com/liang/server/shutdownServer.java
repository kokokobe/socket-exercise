package com.liang.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class shutdownServer extends Thread{
    //服务器线程池
    private ExecutorService executorService;
    // 监听关闭服务器socket
    private ServerSocket serverSocketForShutDown;
    //服务器socket
    private ServerSocket serverSocket;
    // 服务端是否关闭
    private boolean isShutDown = false;
    // 监听关闭端口
    private final int portForShutDown = 8001;
    public shutdownServer(ExecutorService executorService,ServerSocket serverSocket) throws IOException {
        this.executorService=executorService;
        this.serverSocket=serverSocket;
        serverSocketForShutDown = new ServerSocket(portForShutDown);
        // 设置为守护线程
        this.setDaemon(true);
        System.out.println("关闭服务器监听服务启动");
    }
    @Override
    public void run() {
        while (!isShutDown) {
            Socket socketForShutDown = null;
            try {
                socketForShutDown = serverSocketForShutDown.accept();
                // 读取终端输入来关闭socket服务器
                BufferedReader br = new BufferedReader(new InputStreamReader(socketForShutDown.getInputStream()));
                String command = br.readLine();
                if (command.equals("shutdown")) {
                    long beginTime = System.currentTimeMillis();
                    socketForShutDown.getOutputStream().write("服务器正在关闭\r\n".getBytes());
                    isShutDown = true;
                    // 请求关闭线程
                    // 线程池不再接收新的任务，但是会继续执行完工作队列现有的任务
                    executorService.shutdown();
                    // 等待关闭线程池，每次等待的超时时间为30秒
                    while (!executorService.isTerminated()) {
                        executorService.awaitTermination(30, TimeUnit.SECONDS);
                    }
                    // 关闭与EchoServer客户通信的ServerSocket
                    serverSocket.close();
                    long endTime = System.currentTimeMillis();
                    socketForShutDown.getOutputStream().write(
                            ("服务器已经关闭\r\n," + "关闭使用时间" + (endTime - beginTime) + "毫秒\r\n").getBytes());
                    socketForShutDown.close();
                    serverSocketForShutDown.close();
                } else {
                    socketForShutDown.getOutputStream().write("错误命令\r\n".getBytes());
                    socketForShutDown.close();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
