package com.liang.http;

import java.io.IOException;

public interface Content extends Sendable{
    /**
     * 
    * @Title: type
    * @Description: TODO(正文类型)
    * @return 返回类型  String    
     */
    public String type();
    /**
     * 
    * @Title: length
    * @Description: TODO(返回正文的长度，没有prepare之前返回-1)
    * @return 返回类型  long    
     */
    public long length();

    public void prepare() throws IOException;
    /**
     * 发送正文，如果发送完毕，就返回false，否则返回true
     * @throws IOException 
     */
    public boolean send(ChannelIO cio) throws IOException;
    /**
     * 释放文件IO资源
     */
    public void release() throws IOException;

}
