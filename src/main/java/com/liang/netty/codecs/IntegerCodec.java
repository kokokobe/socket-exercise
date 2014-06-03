package com.liang.netty.codecs;

import com.liang.netty.decoder.ToIntegerDecoder;
import com.liang.netty.encoder.IntegerToByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(自由组合的解析器和编码器)
 * @date 2014/6/3.
 */
public class IntegerCodec extends CombinedChannelDuplexHandler<ToIntegerDecoder,IntegerToByteEncoder>{
    public IntegerCodec(){
        super(new ToIntegerDecoder(),new IntegerToByteEncoder());
    }
}
