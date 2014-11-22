/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-22 下午2:44
 * Project Name: socket-exercise
 * File Name: LogEventHandler.java
 */

package com.liang.netty.handler;

import com.liang.netty.udp.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/22.
 * Description:(listen from udp packet and handle log event)
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(msg.getReceivedTimeStamp());
        builder.append(" [");
        builder.append(msg.getSource().toString());
        builder.append("] [");
        builder.append(msg.getLogFile());
        builder.append("] : ");
        builder.append(msg.getMsg());
        System.out.println(builder);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

}
