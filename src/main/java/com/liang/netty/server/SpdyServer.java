package com.liang.netty.server;

import com.liang.netty.Initializer.SpdyChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/8/26.
 * Description:(google spdy server)
 */
public class SpdyServer {
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private final SSLContext context;
    private Channel channel;

    public SpdyServer(SSLContext context) {
        this.context = context;
    }

    public ChannelFuture start(InetSocketAddress socketAddress) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(new SpdyChannelInitializer(context));
        ChannelFuture future = bootstrap.bind(socketAddress);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        SSLContext mainContext = null;
        try {
            mainContext = SSLContext.getDefault();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final SpdyServer server = new SpdyServer(mainContext);
        ChannelFuture future = server.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
