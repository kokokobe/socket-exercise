package com.liang.netty.server;

import com.liang.netty.Initializer.ServerInitializer;
import com.liang.netty.Initializer.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(Using Netty Build WebSocket Server)
 * @date 2014/6/16.
 */
public class WebSocketServer {
    private int port;

    public WebSocketServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootStrap = new ServerBootstrap();
            bootStrap.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new WebSocketServerInitializer());
            ChannelFuture future = bootStrap.bind().sync();
            System.out.println(WebSocketServer.class.getSimpleName() + " started and listen on " + future.channel().localAddress());
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
            System.err.println("Usage: " + WebSocketServer.class.getSimpleName() + " <port>");
        }
        int port = 8080;
        new WebSocketServer(port).start();
    }
}