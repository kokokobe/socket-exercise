package com.liang.netty.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.RandomAccessFile;

/**
 * @author Briliang
 * @date 2014/7/27
 * Description(处理http请求
 * )
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    private final String wsUri;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if(wsUri.equalsIgnoreCase(request.getUri())){
            ctx.fireChannelRead(request.retain());
        }else {
            /**
             * handle http request
             */
            if(HttpHeaders.is100ContinueExpected(request)){
                send100continue(ctx);
            }
            RandomAccessFile file=new RandomAccessFile("C:\\Users\\liang\\Desktop\\phone-revenge.htm","r");
            HttpResponse response=new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/plain;charset=UTF-8");
            boolean keepAlive=HttpHeaders.isKeepAlive(request);
            if(keepAlive){
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
            }
            ctx.write(response);
            if(ctx.pipeline().get(SslHandler.class)==null){
                ctx.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
            }else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future=ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if(!keepAlive){
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    private void send100continue(ChannelHandlerContext ctx) {
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
