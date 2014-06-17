package com.liang.netty.Initializer;

import com.liang.netty.handler.BinaryFrameHandler;
import com.liang.netty.handler.ContinuationFrameHandler;
import com.liang.netty.handler.TextFrameInboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(WebSocket using Netty)
 * @date 2014/6/16.
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new HttpServerCodec(), new HttpObjectAggregator(65536), new WebSocketServerProtocolHandler("/websocket"), new TextFrameInboundHandler(), new BinaryFrameHandler(), new ContinuationFrameHandler());
    }
}
