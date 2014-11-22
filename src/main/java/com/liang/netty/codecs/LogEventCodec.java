/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-22 下午1:09
 * Project Name: socket-exercise
 * File Name: LogEventDecoder.java
 */

package com.liang.netty.codecs;

import com.liang.netty.udp.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/22.
 * Description:(resolve udp datagram packet Message)
 */
public class LogEventCodec extends MessageToMessageCodec<DatagramPacket,LogEvent>{
    private final InetSocketAddress remoteAdr;

    public LogEventCodec(InetSocketAddress remoteAdr) {
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

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf data = datagramPacket.content();
        int i = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);
        String fileName = data.slice(0,i).toString(CharsetUtil.UTF_8);
        String logMsg = data.slice(i+1,data.readableBytes()).toString(CharsetUtil.UTF_8);
        LogEvent event=new LogEvent(datagramPacket.sender(),System.currentTimeMillis(),fileName,logMsg);
        out.add(event);
    }
}
