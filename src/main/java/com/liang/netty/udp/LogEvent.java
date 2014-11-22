/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-20 下午6:34
 * Project Name: socket-exercise
 * File Name: LogEvent.java
 */

package com.liang.netty.udp;

import java.net.InetSocketAddress;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/20.
 * Description:()
 */
public class LogEvent {
    public static final byte SEPARATOR = (byte) '&';
    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long received;

    public LogEvent(InetSocketAddress source, long received, String logFile, String msg) {
        this.source = source;
        this.logFile = logFile;
        this.msg = msg;
        this.received = received;
    }

    public LogEvent(String logFile, String msg) {
        this(null, -1, logFile, msg);
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceivedTimeStamp() {
        return received;
    }
}
