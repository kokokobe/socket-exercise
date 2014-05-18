package com.liang.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @ClassName: HttpServer
 * @Description:(服务器，单线程非阻塞)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2013年12月12日
 */
public class HttpServer {
    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private final int port = 80;
    private final Charset charset = Charset.forName("UTF-8");

    public HttpServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", port);
        serverSocketChannel.bind(socketAddress);
    }

    public void service() {
        SelectionKey key = null;
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new AcceptHandler());
            for (;;) {
                int n = selector.select();
                if (n == 0) {
                    continue;
                }
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> itr = readyKeys.iterator();
                while (itr.hasNext()) {
                    key = (SelectionKey) itr.next();
                    itr.remove();
                    final Handler handler = (Handler) key.attachment();
                    handler.handle(key);
                }
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (key != null) {
                    key.cancel();
                    key.channel().close();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        final HttpServer server=new HttpServer();
        server.service();
    }
}
