package com.liang.netty.handler;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/8/18.
 * Description:()
 */
public class SPDYRequestHandler extends SPDYHttpRequestHandler {
    @Override
    protected String getContent() {
        return "This content is transmitted via SPDY\\r\\n";
    }
}
