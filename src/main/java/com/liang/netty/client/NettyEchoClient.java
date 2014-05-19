package com.liang.netty.client;

import java.net.InetSocketAddress;

import com.liang.netty.Initializer.ClientInitializer;
import com.liang.netty.server.NettyEchoServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
* @ClassName: EchoClient
* @Description:()
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014��4��30��
 */
public class NettyEchoClient {
    private final String host;
    private final int port;
    public NettyEchoClient(String host,int port){
        this.host=host;
        this.port=port;
    }

    public void start(){
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap boot=new Bootstrap();
            boot.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ClientInitializer());
            /**同步等待*/
            ChannelFuture future=boot.connect().sync();
            System.out.println(NettyEchoClient.class.getSimpleName()+" started and connected on "+future.channel().remoteAddress());
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        new NettyEchoClient("127.0.0.1", 8080).start();
    }
}
 