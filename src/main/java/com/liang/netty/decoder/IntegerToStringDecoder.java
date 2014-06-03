package com.liang.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(
 * MessageToMessageDecoder is for Object to Object decoder
 * )
 * @date 2014/6/3.
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer>{
    @Override
    protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        /**
         * 假定msg都是Integer的转换
         */
        out.add(String.valueOf(msg));
    }
}
