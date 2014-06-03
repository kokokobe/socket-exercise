package com.liang.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(构建一个解析Integer的解码器)
 * @date 2014/6/3.
 */
public class ToIntegerDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()>=4){
            out.add(in.readInt());
        }
    }
}
