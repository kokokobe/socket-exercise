package com.liang.netty.Initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @Description:(
 *     支持ssl传输的Initializer
 * )
 * @date 2014/6/4.
 */
public class SslChannelInitializer extends ChannelInitializer<Channel>{
    private final SSLContext context;
    private final boolean client;
    private final boolean startTls;

    public SslChannelInitializer(SSLContext context, boolean client, boolean startTls) {
        this.context = context;
        this.client = client;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine=context.createSSLEngine();
        engine.setUseClientMode(client);
        ch.pipeline().addFirst(new SslHandler(engine,startTls));
    }
}
