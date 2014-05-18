package com.liang.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
/**
 * 
* @ClassName: RequestHandler
* @Description:(������������д����)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2013��12��13��
 */
public class RequestHandler implements Handler {
    private ChannelIO channelIO;
    //���HTTP���󻺳���
    private ByteBuffer requsetByteBuffer=null;
    //��ʾ�Ƿ���յ�����HTTP�������������
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
    * @Description:(����HTTP��������Ѿ������������򷵻�true)
    * @param  key
    * @return ��������  boolean    
    * @throws
     */
    public boolean receive(SelectionKey key) throws IOException{
        ByteBuffer tmp=null;
        if(requestReceived){
            return true;
        }
        //����Ѿ�����ͨ����ĩβ�������Ѿ�����HTTP��������ĩβ��ʶ���ͷ���true
        if((channelIO.read())<0||Request.isComplete(channelIO.getReadBuf())){
            requsetByteBuffer=channelIO.getReadBuf();
            return (requestReceived=true);
        }
        return false;
    }
    /**
     * 
    * @Title: parse
    * @Description:(ͨ��Request���parse����������requestByteBuffer�е�HTTP�������ݣ�
    * ������Ӧ��Request����)
    * @return ��������  boolean    
    * @throws
     */
    private boolean parse(){
        try {
            request=Request.parse(requsetByteBuffer);
            return true;
        } catch (Exception e) {
            //���HTTP�����ʽ����ȷ���ͷ��ʹ�����Ϣ
            response=new Response(Response.Code.BAD_REQUEST,new StringContent(e));
        }
        return false;
    }
    /**
     * 
    * @Title: bulid
    * @Description:(����HTTP��Ӧ)
    * @return ��������  void    
    * @throws
     */
    private void bulid() throws IOException{
        Request.Action action=request.getAction();
        //��֧��GET��HEAD����ʽ
        if((action!=Request.Action.GET)&&(action!=Request.Action.HEAD)){
            response=new Response(Response.Code.METHOD_NOT_ALLOWED,new StringContent("Method Not Allowed"));
        }
        else{
            response=new Response(Response.Code.OK,new FileContent(request.getUri()),action);
        }
    }
    /**
     * ����HTTP���󣬷���HTTP��Ӧ
     */
    @Override
    public void handle(SelectionKey key) throws IOException {
        try {
            //�����û�н��յ�HTTP�������������
            if(request==null){
                //����HTTP����
                if(!receive(key)){
                    return;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
