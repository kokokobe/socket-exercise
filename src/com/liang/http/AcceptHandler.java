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
* @date 2013��12��13��
 */
public class AcceptHandler implements Handler{
    @Override
    public void handle(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel=(ServerSocketChannel) key.channel();
        //�ڷ������������serverSocketChannel.accept()����null
        SocketChannel socketChannel=serverSocketChannel.accept();
        if(socketChannel==null){
            return;
        }
        System.out.println("���ܿͻ������ӣ����ԣ�"+socketChannel.socket().getInetAddress()+":"+socketChannel.socket().getPort());
        ChannelIO cio=new ChannelIO(socketChannel,false);
        RequestHandler rh=new RequestHandler(cio);
        //ע��������¼�����RequestHandler��Ϊ������
        //�������¼�����ʱ����RequestHandler������¼�
        socketChannel.register(key.selector(),SelectionKey.OP_READ,rh);
    }

}
