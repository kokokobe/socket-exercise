package com.liang.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 
* @ClassName: PortScanner
* @Description:
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2013��11��20��
 */
public class PortScanner {
    public static void main(String[] args) throws IOException {
        String host="127.0.0.1";
        if(args.length>0){
            host=args[0];
        }
        new PortScanner().scan(host);
    }
    public void scan(String host) throws IOException{
        //InetAddress localhost=InetAddress.getLocalHost();
        //InetAddress remoteHost=InetAddress.getByName("192.168.37.207");
        Socket socket=null;
        SocketAddress remoteAddr=new InetSocketAddress(host,8080);
        for(int port=1;port<1024;port++){
            try {
                //socket=new Socket(remoteHost,port, localhost, 8000);
                socket=new Socket(host, port);
                //��������ʱ��
                socket.connect(remoteAddr, 60000);
                System.out.println("There is a server on port "+port);
            }
            catch(IOException e){
                System.out.println("can't connect to port "+port);
            }
            finally{
                try {
                    if(socket!=null){
                        socket.close();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
