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
 * @Description: TODO(���߳�socket�����)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013��11��27��
 */
public class EchoServer {
    // �������˿�
    private final int port = 8000;
    private static ServerSocket serverSocket;
    // �̳߳�
    private static ExecutorService executorService;
    // ����CPUʱ�̳߳��еĹ����߳���Ŀ
    private final int POOL_SIZE = 4;
    public EchoServer() throws IOException {
        serverSocket = new ServerSocket(port);
        // �趨�ȴ��ͻ�������ʱ��6��
        serverSocket.setSoTimeout(6000);
        // ����CPU�Ŀ����߳��������̳߳�
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        new shutdownServer(executorService, serverSocket).start();
        System.out.println("�����Ѿ�����");
    }

    public void service() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(6000);
                executorService.execute(new Handler(socket));
            } catch (SocketTimeoutException e) {
                // ���ش���ȴ��ͻ�����ʱ���ֵĳ�ʱ�쳣
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
                // ServerSocket��ShutdownThread�̹߳رն������쳣�����˳�service����
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
