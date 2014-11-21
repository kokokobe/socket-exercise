/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-21 上午10:55
 * Project Name: socket-exercise
 * File Name: LogEventEncoder.java
 */

package com.liang.netty.encoder;

import com.liang.netty.udp.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/21.
 * Description:(encode log entry file )
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAdr;

    public LogEventEncoder(InetSocketAddress remoteAdr) {
        this.remoteAdr = remoteAdr;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(msg.getLogFile().getBytes(CharsetUtil.UTF_8));
        byteBuf.writeByte(LogEvent.SEPARATOR);
        byteBuf.writeBytes(msg.getMsg().getBytes(CharsetUtil.UTF_8));
        out.add(new DatagramPacket(byteBuf,remoteAdr));
    }
}
