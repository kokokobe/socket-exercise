package com.liang.http;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Response implements Sendable{
    static class Code{
        private int number;
        private String reason;
        private Code(int i,String r){
            number=i;reason=r;
        }
        public String toString(){
            return number+":"+reason;
        }
        public static Code OK=new Code(200,"OK");
        static Code BAD_REQUEST=new Code(400,"Bad Request");
        static Code NOT_FOUND=new Code(404,"Not Found");
        static Code METHOD_NOT_ALLOWED=new Code(405,"Method Not Allowed");
    }
    //状态码
    private Code code;
    //响应正文
    private Content content;
    //表示HTTP是否仅包含响应头
    private boolean headersOnly;
    //响应头
    private ByteBuffer headerBuffer=null;
    public Response(Code rc,Content content){
        this(rc,content,null);
    }
    public Response(Code rc,Content content,Request.Action head){
        code=rc;
        this.content=content;
        headersOnly=(head==Request.Action.HEAD);
    }
    private static String CRLF="\r\n";
    private static Charset responseCharset=Charset.forName("UTF-8");
    //创建响应头的内容，把它存放到一个ByteBuffer中
    private ByteBuffer headers(){
        CharBuffer cb=CharBuffer.allocate(1024);
        for(;;){
            try {
                cb.put("HTTP/1.1").put(code.toString()).put(CRLF);
                cb.put("Server:nio/1.1").put(CRLF);
                cb.put("Content-type:").put(content.type()).put(CRLF);
                cb.put("Content-length:").put(Long.toString(content.length())).put(CRLF);
                cb.put(CRLF);
                break;
            } catch (BufferOverflowException e) {
                assert(cb.capacity()<(1<<16));
                cb=CharBuffer.allocate(cb.capacity()*2);
                continue;
            }
        }
        cb.flip();
        return responseCharset.encode(cb);
    }
    /**
     * @throws IOException
     *
     * @Title: prepare
     * @Description:(准备HTTP中响应的正文，以及响应头的内容)
     * @throws
     */
    public void prepare() throws IOException{
        content.prepare();
        headerBuffer=headers();
    }
    /**
     *
     * @Title: send
     * @Description: (发送HTTP响应，如果全部发送完毕返回false,否则返回true)
     * @param cio
     * @return 返回类型  boolean
     * @throws IOException
     */
    public boolean send(ChannelIO cio) throws IOException{
        if(headerBuffer==null){
            throw new IllegalStateException();
        }
        if(headerBuffer.hasRemaining()){
            if(cio.write(headerBuffer)<=0){
                return true;
            }
        }
        if(!headersOnly){
            if(content.send(cio)){
                return true;
            }
        }
        return false;
    }
    /**
     * @throws IOException
     * @Title: release
     * @Description:(释放响应正文占用的资源)
     * @return 返回类型  void
     * @throws
     */
    public void release() throws IOException{
        content.release();
    }
}
