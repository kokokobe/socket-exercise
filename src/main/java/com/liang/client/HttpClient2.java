package com.liang.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
/**
 *
 * @ClassName: HttpClient2
 * @Description: (使用URL类访问http网站)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年2月7日
 */
public class HttpClient2 {
    public static void main(String[] args) throws IOException {
        URL url=new URL("http://baidu.com");
        URLConnection connection=url.openConnection();
        connection.setDoOutput(true);
        //接收响应结果
        System.out.println("正文类型："+connection.getContentType());
        System.out.println("正文长度："+connection.getContentLengthLong());
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
