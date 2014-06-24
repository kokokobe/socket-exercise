package netty;

import com.liang.netty.decoder.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Briliang
 * @date 2014/6/22
 * Description(Test the FixedLengthFrameDecoder )
 */
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFramesDecoded(){
        ByteBuf buf= Unpooled.buffer();
        for (int i=0;i<9;i++){
            buf.writeByte(i);
        }
        ByteBuf input=buf.duplicate();
        EmbeddedChannel channel=new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertTrue(channel.writeInbound(input));
        //mark write finish
        Assert.assertTrue(channel.finish());
        //read message
        Assert.assertEquals(buf.readBytes(3),channel.readInbound());
        Assert.assertEquals(buf.readBytes(3),channel.readInbound());
        Assert.assertEquals(buf.readBytes(3),channel.readInbound());
        Assert.assertNull(channel.readInbound());
    }
    @Test
    public void testFramesDecoded2(){
        ByteBuf buf= Unpooled.buffer();
        for (int i=0;i<9;i++){
            buf.writeByte(i);
        }
        ByteBuf input=buf.duplicate();
        EmbeddedChannel channel=new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        /**
         * mark as write finish
         */
        Assert.assertTrue(channel.finish());
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertEquals(buf.readBytes(3), channel.readInbound());
        Assert.assertNull(channel.readInbound());
    }
}
