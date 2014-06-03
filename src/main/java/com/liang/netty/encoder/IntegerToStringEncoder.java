package com.liang.netty.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(
 *
 * )
 * @date 2014/6/3.
 */
public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer>{
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg));
    }
}
