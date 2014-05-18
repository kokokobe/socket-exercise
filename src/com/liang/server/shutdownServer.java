package com.liang.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class shutdownServer extends Thread{
    //�������̳߳�
    private ExecutorService executorService;
    // �����رշ�����socket
    private ServerSocket serverSocketForShutDown;
    //������socket
    private ServerSocket serverSocket;
    // ������Ƿ�ر�
    private boolean isShutDown = false;
    // �����رն˿�
    private final int portForShutDown = 8001;
    public shutdownServer(ExecutorService executorService,ServerSocket serverSocket) throws IOException {
        this.executorService=executorService;
        this.serverSocket=serverSocket;
        serverSocketForShutDown = new ServerSocket(portForShutDown);
        // ����Ϊ�ػ��߳�
        this.setDaemon(true);
        System.out.println("�رշ�����������������");
    }
    @Override
    public void run() {
        while (!isShutDown) {
            Socket socketForShutDown = null;
            try {
                socketForShutDown = serverSocketForShutDown.accept();
                // ��ȡ�ն��������ر�socket������
                BufferedReader br = new BufferedReader(new InputStreamReader(socketForShutDown.getInputStream()));
                String command = br.readLine();
                if (command.equals("shutdown")) {
                    long beginTime = System.currentTimeMillis();
                    socketForShutDown.getOutputStream().write("���������ڹر�\r\n".getBytes());
                    isShutDown = true;
                    // ����ر��߳�
                    // �̳߳ز��ٽ����µ����񣬵��ǻ����ִ���깤���������е�����
                    executorService.shutdown();
                    // �ȴ��ر��̳߳أ�ÿ�εȴ��ĳ�ʱʱ��Ϊ30��
                    while (!executorService.isTerminated()) {
                        executorService.awaitTermination(30, TimeUnit.SECONDS);
                    }
                    // �ر���EchoServer�ͻ�ͨ�ŵ�ServerSocket
                    serverSocket.close();
                    long endTime = System.currentTimeMillis();
                    socketForShutDown.getOutputStream().write(
                            ("�������Ѿ��ر�\r\n," + "�ر�ʹ��ʱ��" + (endTime - beginTime) + "����\r\n").getBytes());
                    socketForShutDown.close();
                    serverSocketForShutDown.close();
                } else {
                    socketForShutDown.getOutputStream().write("��������\r\n".getBytes());
                    socketForShutDown.close();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
