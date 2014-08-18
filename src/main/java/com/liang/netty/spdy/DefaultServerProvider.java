package com.liang.netty.spdy;

import org.eclipse.jetty.npn.NextProtoNego;
import java.util.Arrays;
import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/8/18.
 * Description:(netty集成google SPDY protocol，provider
 * is used to determine the
   protocol  to  use)
 */
public class DefaultServerProvider implements NextProtoNego.ServerProvider{
    private static final List<String> PROTOCOLS= Arrays.asList("spdy/2","spdy/3","http/1.1");
    private String protocol;
    @Override
    public void unsupported() {
        protocol="http/1.1";
    }

    @Override
    public List<String> protocols() {
        return PROTOCOLS;
    }

    @Override
    public void protocolSelected(String protocol) {
        this.protocol=protocol;
    }
    public String getSelectedProtocol() {
        return protocol;
    }
}
