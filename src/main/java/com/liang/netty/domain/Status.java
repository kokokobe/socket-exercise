/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-24 下午3:47
 * Project Name: socket-exercise
 * File Name: Status.java
 */

package com.liang.netty.domain;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/24.
 * Description:(Memcache response status)
 */
public class Status {
    public final static short NO_ERROR=0x0000;
    public final static short KEY_NOT_FOUND=0x0001;
    public final static short KEY_EXISTS=0x0002;
    public final static short VALUE_TOO_LARGE=0x0003;
    public final static short INVALID_ARGUMENTS=0x0004;
    public final static short ITEM_NOT_STORED=0x0005;
    public final static short INC_DEC_NON_NUM_VAL=0x0006;
}
