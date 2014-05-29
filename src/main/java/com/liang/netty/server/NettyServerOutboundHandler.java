package com.liang.netty.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

import java.net.SocketAddress;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(Netty输出处理器)
 * @date 2014/5/22.
 */
@Sharable
public class NettyServerOutboundHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.writeAndFlush(msg);
        ReferenceCountUtil.release(msg);
        promise.setSuccess();
    }
}
