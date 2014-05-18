package com.liang.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 
* @ClassName: ChannelIO
* @Description:(根据读写自动扩展ByteBuffer缓冲区)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2013年12月12日
 */
public class ChannelIO {
    protected SocketChannel socketChannel;
    //存放请求数据
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
    * @Description:(如果原缓冲区的剩余容量不够，就创建
    * 一个新的缓冲区，容量为原来的两倍，把原来的缓冲区的数据复制到新缓冲区)
    * @param int remaining   
    * @return 返回类型  void    
    * @throws
     */
    protected void resizeRequestBuffer(int remaining) {
        if(requestBuffer.remaining()<remaining){
            ByteBuffer newBuffer=ByteBuffer.allocate(requestBuffer.capacity()*2);
            requestBuffer.flip();
            newBuffer.put(requestBuffer);
            //替换为新的字节缓冲区
            requestBuffer=newBuffer;
        }
    }
    /**
    * @Title: read
    * @Description:(接收数据，把他们存放到requestBuffer中，如果requestBuffer的剩余容量不足5%，
    * 就通过resizeRequestBuffer（）方法扩充容量)
    * @return 返回类型  int    
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
    * @Description:(描述这个方法的作用)
    * @param  FileChannel file
    * @param  long pos
    * @param  long len
    * @return 返回类型  long    
    * @throws
     */
    public long tranferTo(FileChannel file,long pos,long len) throws IOException{
        return file.transferTo(pos,len,socketChannel);
    }
    public void close() throws IOException{
        socketChannel.close();
    }
    
}
