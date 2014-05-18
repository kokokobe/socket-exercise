package com.liang.sender;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender implements Runnable{
    //消息发送次数
    private static volatile int messageSum=0;
    private final String host="localhost";
    private final int port=8000;
    private Socket socket;
    //结束通信方式
    private static int stopWay=1;
    private final int NATURAL_STOP=1;
    private final int SUDDEN_STOP=2;
    private final int SOCKET_STOP=3;
    private final int OUTPUT_STOP=4;
    
    String content ;
    
    public Sender(String content) throws UnknownHostException, IOException {
        this.content = content;
        socket=new Socket();
        //关闭socket及时关闭端口，重用地址
        socket.setReuseAddress(true);
        //等待流数据时间
        socket.setSoTimeout(6000*3);
        SocketAddress socketAddress=new InetSocketAddress(host, port);
        //连接时间
        socket.connect(socketAddress, 600);
        //不使用缓冲区，立即发送数据
        //socket.setTcpNoDelay(true);
        
    }
    private PrintWriter getWriter(Socket socket) throws IOException{
        return new PrintWriter(socket.getOutputStream());
    }
    public void send() throws IOException, InterruptedException{
        PrintWriter pw=getWriter(socket);
        
        for(int i=0;i<20;i++){
            String msg=content+"_"+i;
            pw.println(msg);
            pw.flush();
            System.out.println("send :"+msg+"消息发送次数-->"+(++messageSum));
        }
        if(stopWay==NATURAL_STOP){
            pw.close();
            socket.close();
        }
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        if(args.length>0){
            stopWay=Integer.valueOf(args[0]);
        }
        ExecutorService executorService=Executors.newCachedThreadPool();
        for(int i=0;i<20;i++){
            executorService.execute(new Sender("Test"+i));
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
