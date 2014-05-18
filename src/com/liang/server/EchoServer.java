package com.liang.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 
 * @ClassName: EchoServer
 * @Description: TODO(多线程socket服务端)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013年11月27日
 */
public class EchoServer {
    // 服务器端口
    private final int port = 8000;
    private static ServerSocket serverSocket;
    // 线程池
    private static ExecutorService executorService;
    // 单个CPU时线程池中的工作线程数目
    private final int POOL_SIZE = 4;
    public EchoServer() throws IOException {
        serverSocket = new ServerSocket(port);
        // 设定等待客户端连接时间6秒
        serverSocket.setSoTimeout(6000);
        // 根据CPU的可用线程来创建线程池
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        new shutdownServer(executorService, serverSocket).start();
        System.out.println("服务已经启动");
    }

    public void service() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(6000);
                executorService.execute(new Handler(socket));
            } catch (SocketTimeoutException e) {
                // 不必处理等待客户连接时出现的超时异常
            } catch (RejectedExecutionException e) {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e2) {
                    return;
                }
            } catch (SocketException e) {
                //e.printStackTrace();
                // ServerSocket被ShutdownThread线程关闭而导致异常，就退出service方法
                if (e.getMessage().indexOf("Socket is closed") != -1) {
                    System.out.println(e.getMessage());
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }
}
