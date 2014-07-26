package com.liang.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Briliang
 * @date 2014/7/26
 * Description(处理Integer字节，求绝对值返回)
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf>{
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        while(msg.readableBytes()>=4){
            int value=Math.abs(msg.readInt());
            out.add(value);
        }
    }
}
