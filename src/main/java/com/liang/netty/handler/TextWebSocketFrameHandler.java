package com.liang.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author Briliang
 * @date 2014/7/27
 * Description()
 */
public class TextWebSocketFrameHandler extends ChannelHandlerAdapter{
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt== WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
            ctx.pipeline().remove(HttpRequestHandler.class);
            /**
             * 告知所有的客户端文本帧
             */
            group.writeAndFlush(new TextWebSocketFrame("Client "+ctx.channel()+" joined"));
            group.add(ctx.channel());
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TextWebSocketFrame textWebSocketFrame=new TextWebSocketFrame("Ip:"+ ctx.channel().remoteAddress()+" send message: "+msg);
        group.writeAndFlush(textWebSocketFrame.retain());
    }
/*    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        group.writeAndFlush(msg.retain());
    }*/
}
