package com.liang.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(share EventLoop between BootStrap,less Context switching more efficient)
 * @date 2014/6/19.
 */
public class ShareEventLoopServer {
    public void start(){
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup());
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
            ChannelFuture future;
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                if(future.isDone()){
                    System.out.println("get data is done");
                }
            }

            /**
             *  connect to other server to get data ,so that bootstrap a client to connect
             *  share EventLoop and then no need to switch thread EventLoop
             * @param ctx
             * @throws Exception
             */
            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                super.channelActive(ctx);
                Bootstrap clientBootstrap=new Bootstrap();
                clientBootstrap.channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Receive Data");
                        msg.clear();
                    }
                });
                /**
                 * share the eventLoop with parent
                 */
                clientBootstrap.group(ctx.channel().eventLoop());
                future=clientBootstrap.connect(new InetSocketAddress("http://vip.com", 80));
            }
        });
        ChannelFuture future=bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("server bound");
                }else {
                    System.out.println("Bount attemp failed");
                    future.cause().printStackTrace();
                }

            }
        });
    }
}
