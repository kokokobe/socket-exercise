package com.liang.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
/**
 * 
* @ClassName: HttpClient2
* @Description: (ʹ��URL�����http��վ)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014��2��7��
 */
public class HttpClient2 {
    public static void main(String[] args) throws IOException {
        URL url=new URL("http://baidu.com");
        URLConnection connection=url.openConnection();
        connection.setDoOutput(true);
        //������Ӧ���
        System.out.println("�������ͣ�"+connection.getContentType());
        System.out.println("���ĳ��ȣ�"+connection.getContentLengthLong());
        InputStream in=connection.getInputStream();
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        byte[] buff=new byte[1024];
        int len=-1;
        while((len=in.read(buff))!=-1){
           buffer.write(buff, 0, len); 
        }
        //System.out.println(buffer.toString());
    }
}
