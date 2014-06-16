package com.liang.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(WebSocket处理二进制Handler)
 * @date 2014/6/16.
 */
public class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        System.out.println(msg.toString());
    }
}
