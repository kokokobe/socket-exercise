package com.liang.sender;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        final int  length=100;
        String host="localhost";
        int port=8000;
        Socket[] socket=new Socket[length];
        for(int i=0;i<length;i++){
            socket[i]=new Socket(host,port);
            System.out.println("第"+(i+1)+"次连接成功");
        }
        TimeUnit.MILLISECONDS.sleep(3000);
        for(int i=0;i<length;i++){
            socket[i].close();
        }
    }
}
