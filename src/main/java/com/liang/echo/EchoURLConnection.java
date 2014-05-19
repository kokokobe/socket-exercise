package com.liang.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.SocketChannel;
/**
 *
 * @ClassName: EchoURLConnection
 * @Description: (实现ECHO协议连接请求)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年2月8日
 */
public class EchoURLConnection extends URLConnection {
    private SocketChannel connection=null;
    private static final int DEFAULT_PORT=8000;
    protected EchoURLConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {
        if(!connected){
            int port=url.getPort();
            if(port<0||port>65535){
                port=DEFAULT_PORT;
            }
            SocketAddress socketAddress=new InetSocketAddress(url.getHost(), port);
            this.connection=SocketChannel.open(socketAddress);
            connection.configureBlocking(false);
            this.connected=true;
        }
    }
    public synchronized InputStream getInputStream() throws IOException{
        if(!connected){
            connect();
        }
        return connection.socket().getInputStream();
    }
    public synchronized OutputStream getOutputStream() throws IOException{
        if(!connected){
            connect();
        }
        return connection.socket().getOutputStream();
    }
    public String getContenType() {
        return "text/plain";
    }
    public synchronized void disconnect() throws IOException{
        if(connected){
//            this.connection.socket().close();
            this.connection.close();
            this.connected=false;
        }
    }
}
