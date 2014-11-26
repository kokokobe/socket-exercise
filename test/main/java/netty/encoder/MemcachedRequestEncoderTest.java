/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-24 下午6:39
 * Project Name: socket-exercise
 * File Name: MemcachedRequestEncoderTest.java
 */

package netty.encoder;

import com.liang.netty.domain.MemcachedRequest;
import com.liang.netty.domain.Opcode;
import com.liang.netty.encoder.MemcachedRequestEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/24.
 * Description:()
 */
public class MemcachedRequestEncoderTest {
    @Test
    public void testMemcacheRequestEncoder(){
        MemcachedRequest request = new MemcachedRequest(Opcode.SET,"key1","value1");
        EmbeddedChannel channel = new EmbeddedChannel(new MemcachedRequestEncoder());
        Assert.assertTrue(channel.writeOutbound(request));
        ByteBuf encoded = channel.readOutbound();
        Assert.assertNotNull(encoded);
        /* 二进制字节转化 int时将他转化为无符号扩展 成int*/
        Assert.assertEquals(request.getMagic(),encoded.readByte()&0xff);
        Assert.assertEquals(request.getOpCode(),encoded.readByte());
        Assert.assertEquals(4,encoded.readShort());
        Assert.assertEquals((byte) 0x08, encoded.readByte());
        Assert.assertEquals((byte)0x0,encoded.readByte());
        Assert.assertEquals(0,encoded.readShort());
        Assert.assertEquals(4 + 6 + 8, encoded.readInt());
        Assert.assertEquals(request.getId(), encoded.readInt());
        Assert.assertEquals(request.getCas(), encoded.readLong());
        Assert.assertEquals(request.getFlags(), encoded.readInt());
        Assert.assertEquals(request.getExpires(), encoded.readInt());
        byte[] data = new byte[encoded.readableBytes()];
        encoded.readBytes(data);
        Assert.assertArrayEquals(((request.getKey())+(request.getBody())).getBytes(CharsetUtil.UTF_8),data);
        Assert.assertFalse(encoded.isReadable());
        Assert.assertFalse(channel.finish());
        Assert.assertNull(channel.readInbound());
    }
}
