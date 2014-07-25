package com.liang.netty.server;

import com.liang.netty.Initializer.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @version 1.0
 * @ClassName: EchoServer
 * @Description:(netty开发服务器端)
 * @date 2014/7/25
 */
public class NettyEchoServer {
    private int port;

    public NettyEchoServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ServerInitializer());
            ChannelFuture future = bootStrap.bind().sync();
            System.out.println(NettyEchoServer.class.getSimpleName() + " started and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: " + NettyEchoServer.class.getSimpleName() + " <port>");
        }
        int port = Integer.parseInt(args[0]);
        new NettyEchoServer(port).start();
    }
}
