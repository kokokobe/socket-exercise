package com.liang.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * 
* @ClassName: Target
* @Description:(�������ͻ�����������)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2013��12��10��
 */
public class Target {
    protected InetSocketAddress address;
    SocketChannel channel;
    Exception failure;
    //��ʼ����ʱ��
    protected long connectStart;
    //�������ʱ��
    protected long connectFinish;
    //�������Ƿ��ӡ
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
        //��ӡ���
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
