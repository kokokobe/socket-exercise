package com.liang.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.SelectionKey;

public class Handler implements Runnable {
    private Socket socket;
    private static volatile int sum=0;
    private static volatile int messageSum=0;
    /*
     * private static Handler handler; private Handler(Socket socket){ this.socket=socket; } private Handler(){
     * 
     * } public static Handler getHandler(Socket socket){ if(handler==null){ handler=new Handler(socket); } return
     * handler; }
     */
    public Handler(Socket socket) {
        this.socket = socket;
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String echo(String msg) {
        return msg;
    }

    
    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getName()+" �µ����ӿͻ���--->" + socket.getInetAddress() + ":" + socket.getPort()+"~~~~~�����̴߳�����"+(++sum));
        PrintWriter writer =null;
        BufferedReader reader=null;
        try {
            reader = getReader(socket);
            writer= getWriter(socket);
            String msg = null;
            if(reader!=null){
                while ((msg = reader.readLine()) != null) {
                    System.out.println(Thread.currentThread().getName()+"  --------->   "+msg+"��Ϣ����--->"+(++messageSum));
                    //writer.println(msg);
                    //writer.flush();
                    if (msg.equals("bye")) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if(reader!=null){
                    reader.close();
                }
                if(writer!=null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
