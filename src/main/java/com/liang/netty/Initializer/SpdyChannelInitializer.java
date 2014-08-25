package com.liang.netty.Initializer;

import com.liang.netty.spdy.DefaultServerProvider;
import com.liang.netty.spdy.DefaultSpdyOrHttpChooser;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/8/25.
 * Description:(Spdy Initializer)
 */
public class SpdyChannelInitializer extends ChannelInitializer<SocketChannel>{
    private final SSLContext context;

    public SpdyChannelInitializer(SSLContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        SSLEngine engine= context.createSSLEngine();
        engine.setUseClientMode(false);
        NextProtoNego.put(engine,new DefaultServerProvider());
        NextProtoNego.debug=true;
        pipeline.addLast("SslHandler",new SslHandler(engine));
        pipeline.addLast("chooser",new DefaultSpdyOrHttpChooser(1024*1024,1024*1024));
    }
}
