package com.liang.netty.Initializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(Decoder for the command and the handler,Decoder will return Command Object)
 * @date 2014/6/17.
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(new CmdDecoder(65*1024));
        pipeline.addLast(new CmdHandler());
    }

    private class CmdDecoder extends LineBasedFrameDecoder {
        public CmdDecoder(int maxLength) {
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            ByteBuf frame= (ByteBuf) super.decode(ctx, buffer);
            if(frame==null){
                return null;
            }
            int index=frame.indexOf(frame.readerIndex(),frame.writerIndex(),(byte)' ');
            return new Cmd(frame.slice(frame.readerIndex(),index),frame.slice(index+1,frame.writerIndex()));
        }
    }

    private class CmdHandler extends SimpleChannelInboundHandler<Channel> {
        @Override
        protected void messageReceived(ChannelHandlerContext channelHandlerContext, Channel channel) throws Exception {

        }
    }

    private class Cmd {
        private final ByteBuf name;
        private final ByteBuf args;
        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf getName() {
            return name;
        }

        public ByteBuf getArgs() {
            return args;
        }
    }
}
