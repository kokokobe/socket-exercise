package com.liang.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 *
 * @ClassName: Target
 * @Description:(非阻塞客户端连接任务)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013年12月10日
 */
public class Target {
    protected InetSocketAddress address;
    SocketChannel channel;
    Exception failure;
    //开始连接时间
    protected long connectStart;
    //连接完成时间
    protected long connectFinish;
    //该任务是否打印
    protected boolean shown=false;
    public Target(String host){
        try {
            address=new InetSocketAddress(InetAddress.getByName(host),80);
        } catch (IOException e) {
            e.printStackTrace();
            failure=e;
        }
    }
    public void show(){
        //打印结果
        String result="";
        if(connectFinish!=0){
            result=Long.toString(connectFinish-connectStart)+"ms";
        }else if(failure!=null){
            result=failure.toString();
        }else{
            result="TimeOut";
        }
        System.out.println(address+":"+result);
        shown=true;
    }
}
