package com.liang.netty.Initializer;

import com.liang.netty.handler.HeartbeatHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * @author Briliang
 * @date 2014/7/24
 * Description()
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel>{
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0,0,60, TimeUnit.SECONDS));
        /*pipeline.addLast(new HeartbeatHandler());*/
    }
}
