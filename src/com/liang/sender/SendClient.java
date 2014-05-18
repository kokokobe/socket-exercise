package com.liang.sender;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class SendClient {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        Socket s = new Socket("localhost", 8000);
        /*
         * 由于关闭socket，低层的网络传输数据流并未一定完成
         * 所以设定之后阻塞2秒的时间，关闭socket.close()返回结果
         */
        s.setSoLinger(true, 2);
        /*
         * 输出缓冲区大小
         */
        s.setSendBufferSize(2048);
        /*
         * 接受缓冲区
         */
        s.setReceiveBufferSize(2048);
        OutputStream out = s.getOutputStream();
        out.write("hello,everyone".getBytes());
        out.flush();
/*        for(int i=0;i<2000;i++){
            Socket st=new Socket("localhost", 8000);
            OutputStream out2 = s.getOutputStream();
            out2.write("hello,everyone".getBytes());
            out2.flush();
            st.close();
        }*/
        TimeUnit.MILLISECONDS.sleep(600);
        s.close();
    }
}
