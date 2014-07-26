package com.liang.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(WebSocket处理二进制Handler)
 * @date 2014/6/16.
 */
public class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        //TODO want to send a file binary data
/*        File file = new File("F:\\t_app_startup_info.txt");
        FileInputStream in = new FileInputStream(file);
        FileRegion region = new DefaultFileRegion(in.getChannel(), 0, file.length());
        ByteBuf byteBuf = Unpooled.directBuffer();
        byteBuf.writeBytes("叼死你".getBytes());
        BinaryWebSocketFrame resultFileData=new BinaryWebSocketFrame(byteBuf);
        ctx.writeAndFlush(resultFileData);*/
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {

    }
}
