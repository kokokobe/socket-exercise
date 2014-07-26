package com.liang.netty.Initializer;

import io.netty.channel.*;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.io.Serializable;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(Using JBoss Marshalling)
 * @date 2014/6/18.
 */
public class MarshallingInitializer extends ChannelInitializer<Channel>{
    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
        pipeline.addLast(new MarshallingEncoder(marshallerProvider));
        pipeline.addLast(new ObjectHandler());
    }

    private class ObjectHandler extends SimpleChannelInboundHandler<Serializable>{
        @Override
        protected void messageReceived(ChannelHandlerContext channelHandlerContext, Serializable serializable) throws Exception {

        }
    }
}
