package com.liang.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(
 *     When reading the integer from the inbound ByteBuf, if not enough bytes are readable, it will
 *      throw a signal which will be cached so the decode() method will be called later, once more
 *      data is ready. Otherwise, add it to the List
 * )
 * @date 2014/6/3.
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void>{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readInt());
    }
}
