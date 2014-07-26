package netty.encoder;

import com.liang.netty.encoder.AbsIntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Briliang
 * @date 2014/7/26
 * Description(test IntegerEncoder)
 */
public class AbsIntegerEncoderTest {
    @Test
    public void testEncoded(){
        ByteBuf buf= PooledByteBufAllocator.DEFAULT.buffer();
        for(int i=1;i<10;i++){
            buf.writeInt(i*-1);
        }
        EmbeddedChannel channel=new EmbeddedChannel(new AbsIntegerEncoder());
        Assert.assertTrue(channel.writeOutbound(buf));
        Assert.assertTrue(channel.finish());
        /*ByteBuf output=(ByteBuf)channel.readOutbound();*/
        for(Integer i=1;i<10;i++){
            Assert.assertEquals(i,channel.readOutbound());
        }
        Assert.assertNull(channel.readOutbound());
    }
}
