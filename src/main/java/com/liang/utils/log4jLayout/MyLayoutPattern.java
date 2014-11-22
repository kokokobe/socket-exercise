/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-22 下午5:10
 * Project Name: socket-exercise
 * File Name: MyLayoutPattern.java
 */

package com.liang.utils.log4jLayout;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by liang on 2014/5/17.
 */
public class MyLayoutPattern extends Layout {
    private static Logger logger = LoggerFactory.getLogger(MyLayoutPattern.class);
    private StringBuilder buf = new StringBuilder();
    private static String Prefix;

    @Override
    public String format(LoggingEvent loggingEvent) {
        buf.setLength(0);
        /**
         * 项目前缀
         */
        buf.append(getPrefix()).append("--");
        /**
         * 日志级别
         */
        buf.append("[").append(loggingEvent.getLevel()).append("]").append("--");
        try {
            buf.append("[").append(DateUtil.toStrDateFromUtilDateByFormat(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS)).append("]").append("--");
        } catch (ParseException e) {
            logger.error("log4j layout pattern append dateTime error", e);
        }
        buf.append("[").append(loggingEvent.getLocationInformation().getClassName()).append("]").append("--");
        buf.append("[").append(loggingEvent.getLocationInformation().getMethodName()).append("]").append("--");
        buf.append("[").append(loggingEvent.getRenderedMessage()).append("]");
        buf.append(LINE_SEP);
        return buf.toString();
    }

    @Override
    public boolean ignoresThrowable() {
        return true;
    }

    @Override
    public void activateOptions() {

    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        this.Prefix = prefix;
    }
}
