package com.liang.sender;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender implements Runnable {
    //sum message
    private static volatile int messageSum = 0;
    private final String host = "localhost";
    private final int port = 8000;
    private Socket socket;
    private static int stopWay = 1;
    private final int NATURAL_STOP = 1;
    private final int SUDDEN_STOP = 2;
    private final int SOCKET_STOP = 3;
    private final int OUTPUT_STOP = 4;

    String content;

    public Sender(String content) throws UnknownHostException, IOException {
        this.content = content;
        socket = new Socket();
        //set Reuse
        socket.setReuseAddress(true);
        //设置链接超时
        socket.setSoTimeout(6000 * 3);
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        socket.connect(socketAddress, 600);
        //socket.setTcpNoDelay(true);

    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    public void send() throws IOException, InterruptedException {
        PrintWriter pw = getWriter(socket);
        for (int i = 0; i < 20; i++) {
            String msg = content + "_" + i;
            pw.println(msg);
            pw.flush();
            System.out.println("send :" + msg + "send message count-->" + (++messageSum));
        }
        if (stopWay == NATURAL_STOP) {
            pw.close();
            socket.close();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        if (args.length > 0) {
            stopWay = Integer.valueOf(args[0]);
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Sender("Test" + i));
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        try {
            send();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
