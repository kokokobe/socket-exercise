package com.liang.http;

import java.io.IOException;

public interface Content extends Sendable{
    /**
     * 
    * @Title: type
    * @Description: TODO(��������)
    * @return ��������  String    
     */
    public String type();
    /**
     * 
    * @Title: length
    * @Description: TODO(�������ĵĳ��ȣ�û��prepare֮ǰ����-1)
    * @return ��������  long    
     */
    public long length();

    public void prepare() throws IOException;
    /**
     * �������ģ����������ϣ��ͷ���false�����򷵻�true
     * @throws IOException 
     */
    public boolean send(ChannelIO cio) throws IOException;
    /**
     * �ͷ��ļ�IO��Դ
     */
    public void release() throws IOException;

}
