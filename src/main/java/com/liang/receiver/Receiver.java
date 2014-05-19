package com.liang.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Receiver {
    private final int port = 8000;
    private ServerSocket serverSocket;
    // 结束通信方式
    private static int stopWay = 1;
    private final int NATURAL_STOP = 1;
    private final int SUDDEN_STOP = 2;
    private final int SOCKET_STOP = 3;
    private final int INPUT_STOP = 4;
    private final int SERVERSOCKET_STOP = 5;

    public Receiver() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("服务已经启动");
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        System.out.println(socketIn.available());

        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public void receive() throws IOException, InterruptedException {
        Socket socket = null;
        socket = serverSocket.accept();
        BufferedReader br = getReader(socket);
        for (int i = 0; i < 20; i++) {
            String msg = br.readLine();
            System.out.println("receive:" + msg);
            TimeUnit.MILLISECONDS.sleep(1000);
            if (i == 2) {
                if (stopWay == SUDDEN_STOP) {
                    System.out.println("突然终止程序");
                    System.exit(0);
                }
                if (stopWay == SOCKET_STOP) {
                    System.out.println("关闭Socket并终止程序");
                    socket.close();
                    break;
                }
                if (stopWay == INPUT_STOP) {
                    System.out.println("关闭输入流并终止程序");
                    socket.shutdownInput();
                    break;
                }
                if (stopWay == SERVERSOCKET_STOP) {
                    System.out.println("关闭ServerSocket并终止程序");
                    serverSocket.close();
                    break;
                }
            }
        }
        if(stopWay==NATURAL_STOP){
            socket.close();
            serverSocket.close();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length>0){
            stopWay=Integer.valueOf(args[0]);
        }
        new Receiver().receive();
    }
}
