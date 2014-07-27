package com.liang.netty.handler;

/**
 * @author Briliang
 * @date 2014/7/24
 * Description(处理心跳handler)
 */
public class HeartbeatHandler {
/*    private static final ByteBuf HEARTBEAT_SEQUENCE= Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }*/
}
