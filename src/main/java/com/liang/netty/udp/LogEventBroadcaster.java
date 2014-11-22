/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-21 下午4:48
 * Project Name: socket-exercise
 * File Name: LogEventBroadcaster.java
 */

package com.liang.netty.udp;

import com.liang.netty.codecs.LogEventCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/21.
 * Description:(config broadcaster channelPipeLine)
 */
public class LogEventBroadcaster {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress socketAddress, File file) {
        this.eventLoopGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap().group(eventLoopGroup).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true).handler(new LogEventCodec(socketAddress));
        this.file = file;
    }

    public void run() throws IOException {
        Channel channel = bootstrap.bind(0).syncUninterruptibly().channel();
        long pointer = 0;
        for (; ; ) {
            long len = file.length();
            if (len <= pointer) {
                /*file was reset*/
                pointer = len;
            } else if (len > pointer) {
                /*Content was added*/
                /* read only*/
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                randomAccessFile.seek(pointer);
                String line;
                while ((line = randomAccessFile.readLine()) != null) {
                    channel.writeAndFlush(new LogEvent(null, -1, file.getAbsolutePath(), line));
                }
                pointer = randomAccessFile.getFilePointer();
                randomAccessFile.close();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void shutdown() {
        eventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws UnknownHostException {
        String filePath = "C:\\Users\\lwl\\.IntelliJIdea14\\system\\tomcat\\Unnamed_vips-mobile-operation\\logs\\catalina.2014-11-11.log";
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(new InetSocketAddress(InetAddress.getLocalHost(), 9090), new File(filePath));
        try {
            broadcaster.run();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            broadcaster.shutdown();
        }

    }
}
