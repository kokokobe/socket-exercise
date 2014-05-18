package com.liang.receiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port = 8000;
    private ServerSocket serverSocket;

    public Server() throws IOException {
        // 队列长度为3
        serverSocket = new ServerSocket(port, 3,InetAddress.getByName("127.0.0.1"));
        System.out.println("服务已经启动");
    }

    public void service() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("新的连接socket请求-->" + socket.getInetAddress() + ":" + socket.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // 判断socket是否已经连接，并且没有关闭
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
