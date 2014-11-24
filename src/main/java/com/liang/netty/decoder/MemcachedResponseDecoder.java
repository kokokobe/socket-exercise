/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-24 下午5:29
 * Project Name: socket-exercise
 * File Name: MemcachedResponseDecoder.java
 */

package com.liang.netty.decoder;

import com.liang.netty.domain.MemcachedResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/24.
 * Description:(decode byte stream to response obj
 * The class that is responsible to create the MemcachedResponse out of the read bytes
Represent current parsing state which means we either need to parse the header or body next
Switch based on the parsing state
If not at least 24 bytes are readable it is impossible to read the whole header, so return here and wait
to get notified again once more data is ready to be read
Read all fields out of the header
Check if enough data is readable to read the complete response body. The length was read out of header before
Check if there are any extra flags to read and if so do it
Check if the response contains an expire field and if so read it
check if the response contains a key and if so read it
Read the actual body payload
Construct a new MemachedResponse out of the previous read fields and data)
 */
public class MemcachedResponseDecoder extends ByteToMessageDecoder {
    private enum State {
        Header, Body
    }

    private State state = State.Header;
    private int totalBodySize;
    private byte magic;
    private byte opCode;
    private short keyLength;
    private byte extraLength;
    private byte dataType;
    private short status;
    private int id;
    private long cas;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state) {
            case Header:
                if (in.readableBytes() < 24) {
                    /* response header is 24 bytes */
                    return;
                }
                /* read byte */
                magic = in.readByte();
                opCode = in.readByte();
                keyLength = in.readShort();
                extraLength = in.readByte();
                dataType = in.readByte();
                status = in.readShort();
                totalBodySize = in.readInt();
                id = in.readInt();
                cas = in.readLong();
                /* fallthrough and start to read the body */
                state = State.Body;
            case Body:
                if (in.readableBytes() < totalBodySize) {
                    /* until we have the entire payload return */
                    return;
                }
                int flags = 0, expires = 0;
                int actualBodySize = totalBodySize;
                if (extraLength > 0) {
                    flags = in.readInt();
                    actualBodySize -= 4;
                }
                if (extraLength > 4) {
                    expires = in.readInt();
                    actualBodySize -= 4;
                }
                String key = "";
                if (keyLength > 0) {
                    ByteBuf byteBuf = in.readBytes(keyLength);
                    key = byteBuf.toString(CharsetUtil.UTF_8);
                    actualBodySize -= keyLength;
                }
                ByteBuf body = in.readBytes(actualBodySize);
                String data = body.toString(CharsetUtil.UTF_8);
                out.add(new MemcachedResponse(magic, opCode, dataType, status, id, cas, flags, expires, key, data));
                state = State.Header;
        }
    }
}
