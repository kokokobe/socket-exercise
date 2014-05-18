package com.liang.http;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
/**
 * 
* @ClassName: AcceptHandler
* @Description:()
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2013年12月13日
 */
public class AcceptHandler implements Handler{
    @Override
    public void handle(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel=(ServerSocketChannel) key.channel();
        //在非阻塞的情况下serverSocketChannel.accept()返回null
        SocketChannel socketChannel=serverSocketChannel.accept();
        if(socketChannel==null){
            return;
        }
        System.out.println("接受客户端连接，来自："+socketChannel.socket().getInetAddress()+":"+socketChannel.socket().getPort());
        ChannelIO cio=new ChannelIO(socketChannel,false);
        RequestHandler rh=new RequestHandler(cio);
        //注册读就绪事件，把RequestHandler作为附件，
        //当这种事件发生时，由RequestHandler处理该事件
        socketChannel.register(key.selector(),SelectionKey.OP_READ,rh);
    }

}
