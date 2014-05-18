package com.liang.multicast.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

/**
 * 
 * @ClassName: MulticastSender
 * @Description:(组播发送类)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年3月27日
 * @version
 * @since
 */
public class MulticastSender {
    /* 组播地址 */
    private static InetAddress group;
    private static final int port = 4000;
    private MulticastSocket ms;

    public MulticastSender() {

    }

    public MulticastSender(int port,String host) throws IOException {
        ms = new MulticastSocket(port);
        group=InetAddress.getByName(host);
    }

    public static void main(String[] args) {
        MulticastSender sender;
        MulticastSocket ms = null;
        try {
            sender = new MulticastSender(port,"224.0.0.1");
            ms=sender.getMs();
            ms.joinGroup(group);
            while(true){
                String message="Hello"+new Date();
                byte[] buffer=message.getBytes();
                DatagramPacket datapacket=new DatagramPacket(buffer,buffer.length,group,port);
                ms.send(datapacket);
                System.out.println("发送数据报给:"+group+":"+port);
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            if(ms!=null){
                try {
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public MulticastSocket getMs() {
        return ms;
    }

    public void setMs(MulticastSocket ms) {
        this.ms = ms;
    }
}
