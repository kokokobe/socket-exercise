package com.liang.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.StringUtil;

import java.net.InetSocketAddress;

/**
 * @author Briliang
 * @date 2014/6/21
 * Description(Example:IllegalStateException thrown due to invalid configuration)
 */
public class MakingUserChannelOpts {
    private final AttributeKey<Integer> id=new AttributeKey<>("ID");
    public void start(){
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class);
        bootstrap.handler(new SimpleChannelInboundHandler<ByteBuf>() {
            @Override
            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                super.channelRegistered(ctx);
                Integer value=ctx.channel().attr(id).get();
            }

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                System.out.println("Receive Data:");
                while (msg.isReadable()) {
                    System.out.print(msg.readByte());
                }
                msg.clear();
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
        bootstrap.attr(id,123456);
        ChannelFuture future=bootstrap.connect(new InetSocketAddress("www.vip.com", 80));
        future.syncUninterruptibly();
    }

    public static void main(String[] args) {
        new MakingUserChannelOpts().start();
    }
}
