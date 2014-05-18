package com.liang.receiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port = 8000;
    private ServerSocket serverSocket;

    public Server() throws IOException {
        // ���г���Ϊ3
        serverSocket = new ServerSocket(port, 3,InetAddress.getByName("127.0.0.1"));
        System.out.println("�����Ѿ�����");
    }

    public void service() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("�µ�����socket����-->" + socket.getInetAddress() + ":" + socket.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // �ж�socket�Ƿ��Ѿ����ӣ�����û�йر�
                    boolean isConnected = socket.isConnected() && !socket.isClosed();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server=new Server();
        //TimeUnit.MILLISECONDS.sleep(60000*10);
        server.service();
    }
}
