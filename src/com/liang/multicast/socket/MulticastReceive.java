package com.liang.multicast.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;


public class MulticastReceive {
    private InetAddress group;
    private int port=4000;
    private MulticastSocket ms;
    public MulticastReceive(){
        
    }
    public MulticastReceive(String host) throws IOException{
        group=InetAddress.getByName(host);;
        ms=new MulticastSocket(port);
    }
    public static void main(String[] args) {
        MulticastSocket ms = null;
        InetAddress group=null;
        try {
            MulticastReceive receive=new MulticastReceive("224.0.0.1");
            ms=receive.getMs();
            group=receive.getGroup();
            ms.joinGroup(group);
            byte[] buffer=new byte[8192];
            while(true){
                DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
                ms.receive(dp);
                String receiveMessage=new String(dp.getData(),0,dp.getLength());
                System.out.println(receiveMessage);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(ms!=null){
                try {
                    ms.leaveGroup(group);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ms.close();
            }
        }
    }
    public MulticastSocket getMs() {
        return ms;
    }
    public void setMs(MulticastSocket ms) {
        this.ms = ms;
    }
    public InetAddress getGroup() {
        return group;
    }
    public void setGroup(InetAddress group) {
        this.group = group;
    }
}
 