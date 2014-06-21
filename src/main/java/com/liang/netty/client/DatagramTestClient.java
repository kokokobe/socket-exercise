package com.liang.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @author Briliang
 * @date 2014/6/21
 * Description(Using Bootstrap with DatagramChannel)
 */
public class DatagramTestClient {
    public static void main(String[] args) {
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(new OioEventLoopGroup()).channel(OioDatagramChannel.class);
        bootstrap.handler(new SimpleChannelInboundHandler<ByteBuf>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                //TODO something to read
            }
        });
        ChannelFuture future=bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("Channel bound");
                }else {
                    System.err.println("Bound attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
