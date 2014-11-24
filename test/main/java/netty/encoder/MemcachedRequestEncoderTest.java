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
import io.netty.channel.embedded.EmbeddedChannel;
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
    }
}
