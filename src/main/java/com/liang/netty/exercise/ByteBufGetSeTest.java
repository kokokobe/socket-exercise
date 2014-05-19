package com.liang.netty.exercise;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(netty ByteBuf ,get set ByteBuf的时候,wrirteIndex 和readerIndex的变化)
 * @date 2014/5/19.
 */
public class ByteBufGetSeTest {
    private static Charset utf8 = Charset.forName("UTF-8");
    private static ByteBuf buf = Unpooled.copiedBuffer("Netty in Briliang rock!", utf8);

    public static void main(String[] args) {
        System.out.println((char) buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.setByte(0,(byte)'b');
        System.out.println((char)buf.getByte(0));
        assert readerIndex==buf.readerIndex();
        assert writerIndex==buf.writerIndex();
    }

}
