package com.liang.netty.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.nio.charset.Charset;

@Sharable
public class NettyServerInboundHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
        // 返回客户端消息 - 我已经接收到了你的消息
        ctx.writeAndFlush("Received your message !").addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Write Successful");
                } else {
                    System.out.println("Write Error");
                    future.cause().printStackTrace();
                }
            }
        });
        System.out.println("channel doesn't close flag：" + ctx.channel().isOpen());

    }

/*    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " +msg);
        // 返回客户端消息 - 我已经接收到了你的消息
        ctx.writeAndFlush("Received your message !\n");
    }*/

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client address : "+ctx.channel().remoteAddress());
        ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!");
        /*super.channelActive(ctx);*/
        /**
         * 通过slice截取一部分ByteBuf来修改其中的内容会共享在原有的buf中
         */
        Charset utf8= Charset.forName("utf-8");
        ByteBuf buf=Unpooled.copiedBuffer("Netty in BriLiang study",utf8);
        ByteBuf sliced=buf.slice(0,14);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0,(byte)'j');
        assert buf.getByte(0)==sliced.getByte(0);
        System.out.println("断言成功:"+(buf.getByte(0)==sliced.getByte(0)));
    }

    /*    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server received: "+msg);
        ctx.write(msg);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }*/

}
 