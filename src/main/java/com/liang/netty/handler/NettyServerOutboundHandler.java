package com.liang.netty.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(Netty输出处理器)
 * @date 2014/5/22.
 */
@Sharable
public class NettyServerOutboundHandler extends ChannelHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.writeAndFlush(msg);
        ReferenceCountUtil.release(msg);
        promise.setSuccess();
    }
}
