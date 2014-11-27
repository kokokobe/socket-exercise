/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-27 下午3:43
 * Project Name: socket-exercise
 * File Name: DeregisterAndRegisterOwnChannel.java
 */

package netty;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/27.
 * Description:()
 */
public class DeregisterAndRegisterOwnChannel {
    SocketChannel mySocketChannel;
    public DeregisterAndRegisterOwnChannel() throws IOException {
        mySocketChannel = SocketChannel.open();
        EventLoopGroup eventLoopGroup =new NioEventLoopGroup();
        /*
        EventLoop eventLoop = new NioEventLoop(eventLoopGroup, Executors.newCachedThreadPool(),null);
        NioSocketChannel channel = new NioSocketChannel(mySocketChannel);*/


    }


}
