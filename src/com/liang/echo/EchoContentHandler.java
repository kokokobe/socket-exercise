package com.liang.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.URLConnection;
/**
 * 
* @ClassName: EchoContentHandler
* @Description:(������EchoServer�ķ�������)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014��2��10��
 */
public class EchoContentHandler extends ContentHandler {
    /**
     * ��ȡ���������͵�һ�����ݣ�����ת�����ַ�������
     */
    @Override
    public Object getContent(URLConnection connection) throws IOException {
        InputStream in=connection.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        return br.readLine();
    }
    @Override
    public Object getContent(URLConnection connection,Class[] classes) throws IOException {
        InputStream in=connection.getInputStream();
        for(int i=0;i<classes.length;i++){
            if(classes[i]==InputStream.class){
                return in;
            }
            else if(classes[i]==String.class){
                return getContent(connection);
            }
        }
        return null;
    }
}   
