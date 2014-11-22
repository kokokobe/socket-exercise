/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-22 下午3:04
 * Project Name: socket-exercise
 * File Name: LogEventMonitor.java
 */

package com.liang.netty.udp;

import com.liang.netty.codecs.LogEventCodec;
import com.liang.netty.handler.LogEventHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.net.InetSocketAddress;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/22.
 * Description:(receive udp broadcaster msg)
 */
public class LogEventMonitor {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;

    public LogEventMonitor(InetSocketAddress inetSocketAddress) {
        eventLoopGroup =  new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LogEventCodec(inetSocketAddress));
                pipeline.addLast(new LogEventHandler());
            }
        }).localAddress(inetSocketAddress);
    }

    public Channel bind(){
        return bootstrap.bind().syncUninterruptibly().channel();
    }

    public void shutdown(){
        eventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(9090));
        Channel channel = monitor.bind();
        System.out.println("LogEventMonitor running");
        try {
            channel.closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            monitor.shutdown();
        }
    }


}
