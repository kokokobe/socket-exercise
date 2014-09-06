package com.liang.netty.spdy;

import com.liang.netty.handler.SpdyHttpRequestHandler;
import com.liang.netty.handler.SpdyRequestHandler;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLEngine;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/8/18.
 * Description:(判断逻辑,判断HTTP or spdy protocols)
 */
public class DefaultSpdyOrHttpChooser extends SpdyOrHttpChooser{
    public DefaultSpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
        super(maxSpdyContentLength, maxHttpContentLength);
    }

    @Override
    protected SelectedProtocol getProtocol(SSLEngine engine) {
        DefaultServerProvider provider= (DefaultServerProvider) NextProtoNego.get(engine);
        String protocol=provider.getSelectedProtocol();
        if(protocol==null){
            return SelectedProtocol.UNKNOWN;
        }
        switch (protocol){
            case "spdy/2" :return SelectedProtocol.UNKNOWN;
            case "spdy/3" :return SelectedProtocol.SPDY_3;
            case "http/1.1" :return SelectedProtocol.HTTP_1_1;
        }
        return null;
    }

    @Override
    protected ChannelHandler createHttpRequestHandlerForHttp() {
        return new SpdyHttpRequestHandler();
    }

    @Override
    protected ChannelHandler createHttpRequestHandlerForSpdy() {
        return new SpdyRequestHandler();
    }
}
