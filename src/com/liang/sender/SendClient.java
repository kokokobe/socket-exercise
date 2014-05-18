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
         * ���ڹر�socket���Ͳ�����紫����������δһ�����
         * �����趨֮������2���ʱ�䣬�ر�socket.close()���ؽ��
         */
        s.setSoLinger(true, 2);
        /*
         * �����������С
         */
        s.setSendBufferSize(2048);
        /*
         * ���ܻ�����
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
