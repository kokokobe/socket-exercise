package com.liang.http;

import java.io.IOException;

/**
 * 
* @ClassName: Sendable
* @Description: TODO(�������ɷ��͸��ͻ�������)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014��1��3��
 */
public interface Sendable {
    /**
     * 
    * @Title: prepare
    * @Description:(׼�����͵�����)
    * @return ��������  void    
    * @throws IOException
     */
    public void prepare() throws IOException;
    /**
     * 
    * @Title: send
    * @Description: TODO(����ͨ���������ݣ������������򷵻�false���򷵻�true���������û׼�����򷵻�IllegalException)
    * @param  cio
    * @return ��������  boolean    
    * @throws IOException
     */
    public boolean send(ChannelIO cio) throws IOException;
    /**
     * 
    * @Title: release
    * @Description: TODO(��������������Ϻ󣬾͵��ô˷������ͷ�ռ�õ���Դ)
    * @return ��������  void    
    * @throws IOException
     */
    public void release() throws IOException;
}
