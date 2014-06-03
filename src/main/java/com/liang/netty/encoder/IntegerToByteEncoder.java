package com.liang.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(
 *  把Integer编码放入Channel
 * )
 * @date 2014/6/3.
 */
public class IntegerToByteEncoder extends MessageToByteEncoder<Short>{
    @Override
    protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
        out.writeShort(msg);
    }
}
