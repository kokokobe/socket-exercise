package com.liang.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
/**
 * 
* @ClassName: RequestHandler
* @Description:(处理接受请求读写就绪)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2013年12月13日
 */
public class RequestHandler implements Handler {
    private ChannelIO channelIO;
    //存放HTTP请求缓冲区
    private ByteBuffer requsetByteBuffer=null;
    //表示是否接收到所有HTTP请求的所有数据
    private boolean requestReceived=false;
    private Request request=null;
    private Response response=null;
    public RequestHandler(ChannelIO cio) {
        this.channelIO=cio;
    }
    /**
     * @throws IOException 
     * 
    * @Title: receive
    * @Description:(接受HTTP请求，如果已经接收完数据则返回true)
    * @param  key
    * @return 返回类型  boolean    
    * @throws
     */
    public boolean receive(SelectionKey key) throws IOException{
        ByteBuffer tmp=null;
        if(requestReceived){
            return true;
        }
        //如果已经读到通道的末尾，或者已经读到HTTP请求数据末尾标识，就返回true
        if((channelIO.read())<0||Request.isComplete(channelIO.getReadBuf())){
            requsetByteBuffer=channelIO.getReadBuf();
            return (requestReceived=true);
        }
        return false;
    }
    /**
     * 
    * @Title: parse
    * @Description:(通过Request类的parse方法，解析requestByteBuffer中的HTTP请求数据，
    * 构造相应的Request对象)
    * @return 返回类型  boolean    
    * @throws
     */
    private boolean parse(){
        try {
            request=Request.parse(requsetByteBuffer);
            return true;
        } catch (Exception e) {
            //如果HTTP请求格式不正确，就发送错误信息
            response=new Response(Response.Code.BAD_REQUEST,new StringContent(e));
        }
        return false;
    }
    /**
     * 
    * @Title: bulid
    * @Description:(创建HTTP响应)
    * @return 返回类型  void    
    * @throws
     */
    private void bulid() throws IOException{
        Request.Action action=request.getAction();
        //仅支持GET和HEAD请求方式
        if((action!=Request.Action.GET)&&(action!=Request.Action.HEAD)){
            response=new Response(Response.Code.METHOD_NOT_ALLOWED,new StringContent("Method Not Allowed"));
        }
        else{
            response=new Response(Response.Code.OK,new FileContent(request.getUri()),action);
        }
    }
    /**
     * 接受HTTP请求，发送HTTP响应
     */
    @Override
    public void handle(SelectionKey key) throws IOException {
        try {
            //如果还没有接收到HTTP请求的所有数据
            if(request==null){
                //接受HTTP请求
                if(!receive(key)){
                    return;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
