package com.liang.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 
* @ClassName: ChannelIO
* @Description:(���ݶ�д�Զ���չByteBuffer������)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2013��12��12��
 */
public class ChannelIO {
    protected SocketChannel socketChannel;
    //�����������
    protected ByteBuffer requestBuffer;
    private static int requestBufferSize=4096;
    public ChannelIO(SocketChannel socketChannel,boolean blocking) throws IOException{
        this.socketChannel=socketChannel;
        this.socketChannel.configureBlocking(blocking);
        requestBuffer=ByteBuffer.allocate(requestBufferSize);
    }
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
    /**
     * 
    * @Title: resizeRequestBuffer
    * @Description:(���ԭ��������ʣ�������������ʹ���
    * һ���µĻ�����������Ϊԭ������������ԭ���Ļ����������ݸ��Ƶ��»�����)
    * @param int remaining   
    * @return ��������  void    
    * @throws
     */
    protected void resizeRequestBuffer(int remaining) {
        if(requestBuffer.remaining()<remaining){
            ByteBuffer newBuffer=ByteBuffer.allocate(requestBuffer.capacity()*2);
            requestBuffer.flip();
            newBuffer.put(requestBuffer);
            //�滻Ϊ�µ��ֽڻ�����
            requestBuffer=newBuffer;
        }
    }
    /**
    * @Title: read
    * @Description:(�������ݣ������Ǵ�ŵ�requestBuffer�У����requestBuffer��ʣ����������5%��
    * ��ͨ��resizeRequestBuffer����������������)
    * @return ��������  int    
    * @throws
     */
    public int read() throws IOException{
        resizeRequestBuffer(requestBufferSize/20);
        return socketChannel.read(requestBuffer);
    }
    public ByteBuffer getReadBuf(){
        return requestBuffer;
    }
    public int write(ByteBuffer bytebuffer) throws IOException{
        return socketChannel.write(bytebuffer);
    }
    /**
     * @throws IOException 
     * 
    * @Title: tranferTo
    * @Description:(�����������������)
    * @param  FileChannel file
    * @param  long pos
    * @param  long len
    * @return ��������  long    
    * @throws
     */
    public long tranferTo(FileChannel file,long pos,long len) throws IOException{
        return file.transferTo(pos,len,socketChannel);
    }
    public void close() throws IOException{
        socketChannel.close();
    }
    
}
