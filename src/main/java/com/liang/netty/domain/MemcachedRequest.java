/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-24 下午3:12
 * Project Name: socket-exercise
 * File Name: MemcachedRequest.java
 */

package com.liang.netty.domain;


import java.util.Random;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/24.
 * Description:(need to encode Memcache protocol)
 */
public class MemcachedRequest {
    private static final Random rand = new Random();
    /*fixed so hard coded*/
    private int magic = 0x80;
    /*the operation code*/
    private byte opCode ;
    private String key;
    /* random */
    private int flags = 0xdeadbeef;
    /*0 = item never expires*/
    private int expires;
    /* if opCode is set, the value */
    private String body;
    /* Opaque */
    private int id = rand.nextInt();
    /* data version check...not used */
    private long cas ;
    /* not all ops have extras*/
    private boolean hasExtras;

    public MemcachedRequest(byte opCode, String key, String body) {
        this.opCode = opCode;
        this.key = key;
        this.body = body == null? "":body;
        hasExtras = opCode == Opcode.SET;
    }

    public static Random getRand() {
        return rand;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public byte getOpCode() {
        return opCode;
    }

    public void setOpCode(byte opCode) {
        this.opCode = opCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCas() {
        return cas;
    }

    public void setCas(long cas) {
        this.cas = cas;
    }

    public boolean isHasExtras() {
        return hasExtras;
    }

    public void setHasExtras(boolean hasExtras) {
        this.hasExtras = hasExtras;
    }
}
