package com.liang.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(WebSocket 处理字符串handler)
 * @date 2014/6/16.
 */
public class TextFrameInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println(msg.text());
        TextWebSocketFrame resultText = new TextWebSocketFrame("BriLiang Server Echo: " + msg.text());
        ctx.writeAndFlush(resultText);
    }
}
