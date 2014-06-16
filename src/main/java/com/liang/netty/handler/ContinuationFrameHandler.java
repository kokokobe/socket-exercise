package com.liang.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:()
 * @date 2014/6/16.
 */
public class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
        System.out.println(msg.toString());
    }
}
