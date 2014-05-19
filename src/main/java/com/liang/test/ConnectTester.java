package com.liang.test;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ConnectTester {
    public static void main(String[] args) {
        String host="localhost";
        int port=80;
        if(args.length>0){
            host=args[0];
            port=Integer.parseInt(args[1]);
        }
        new ConnectTester().connect(host, port);
    }
    public void connect(String host,int port){
        String result="";
        Socket socket=null;
        SocketAddress remoteAddress=null;
        try {
            remoteAddress=new InetSocketAddress(host, port);
            long begin=System.currentTimeMillis();
            socket=new Socket();
            socket.connect(remoteAddress,60000);
            long end=System.currentTimeMillis();
            result=end-begin+"ms";
        } catch (BindException e) {
            result="Local address and port can't be binded";
        }catch (UnknownHostException e) {
            result="Unknown Host";
        }catch (ConnectException e) {
            result="Connection Refused";
        }catch (SocketTimeoutException e) {
            result="Time Out";
        }catch (IOException e) {
            result="failure";
        }finally{
            try {
                //判断socket是否已经连接，并且没有关闭
                boolean isConnected=socket.isConnected()&&!socket.isClosed();
                System.out.println(isConnected);
                if(isConnected){
                    socket.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        System.out.println(remoteAddress+":"+result);

    }

}
