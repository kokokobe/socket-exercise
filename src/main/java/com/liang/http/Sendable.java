package com.liang.http;

import java.io.IOException;

/**
 *
 * @ClassName: Sendable
 * @Description: TODO(服务器可发送给客户的内容)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年1月3日
 */
public interface Sendable {
    /**
     *
     * @Title: prepare
     * @Description:(准备发送的内容)
     * @return 返回类型  void
     * @throws IOException
     */
    public void prepare() throws IOException;
    /**
     *
     * @Title: send
     * @Description: TODO(利用通道发送内容，如果发送完毕则返回false否则返回true，如果内容没准备好则返回IllegalException)
     * @param  cio
     * @return 返回类型  boolean
     * @throws IOException
     */
    public boolean send(ChannelIO cio) throws IOException;
    /**
     *
     * @Title: release
     * @Description: TODO(当服务器发送完毕后，就调用此方法，释放占用的资源)
     * @return 返回类型  void
     * @throws IOException
     */
    public void release() throws IOException;
}
