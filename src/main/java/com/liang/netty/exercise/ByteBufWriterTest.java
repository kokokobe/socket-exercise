package com.liang.netty.exercise;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(netty ByteBuf ,writeByteBuf的时候,wrirteIndex的变化)
 * @date 2014/5/19.
 */
public class ByteBufWriterTest {
    private static Charset utf8 = Charset.forName("UTF-8");
    private static ByteBuf buf = Unpooled.copiedBuffer("Netty in Briliang rock!", utf8);

    public static void main(String[] args) {
        System.out.println((char) buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.writeByte((byte)'?');
        assert readerIndex==buf.readerIndex();
        assert writerIndex!=buf.writerIndex();
    }
    
}
