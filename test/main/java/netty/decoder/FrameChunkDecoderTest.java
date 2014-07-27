package netty.decoder;

import com.liang.netty.decoder.FrameChunkDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Briliang
 * @date 2014/7/27
 * Description(test FrameChunkDecoder)
 */
public class FrameChunkDecoderTest {
    @Test
    public void testFramesDecoded(){
        ByteBuf buf= PooledByteBufAllocator.DEFAULT.buffer();
        for(int i=0;i<9;i++){
            buf.writeByte(i);
        }
        ByteBuf input=buf.duplicate();
        EmbeddedChannel channel=new EmbeddedChannel(new FrameChunkDecoder(3));
        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        }catch (TooLongFrameException e){

        }
        Assert.assertTrue(channel.writeInbound(input.readBytes(3)));
        Assert.assertTrue(channel.finish());
        Assert.assertEquals(buf.readBytes(2),channel.readInbound());
        Assert.assertEquals(buf.skipBytes(4).readBytes(3),channel.readInbound());
    }
}
