package com.liang.echo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class EchoCllient {
    public static void main(String[] args) throws IOException {
        EchoCllient echoClient=new EchoCllient();
        EchoURLConnection con=echoClient.initData();
        echoClient.ouputDataToService(con);
    }
    
    /**
    * @Title: initData
    * @Description: (�����������������)
    * @throws MalformedURLException
    * @throws IOException   
    * @return ��������  URLConnection    
    * @throws
    */ 
    private EchoURLConnection initData() throws MalformedURLException, IOException {
        //����URLStreamHandlerFactory
        URL.setURLStreamHandlerFactory(new EchoURLStreamHandlerFactory());
        //����ContentHandlerFactory
        URLConnection.setContentHandlerFactory(new EchoContentHandlerFactory());
        URL url=new URL("echo://localhost:8000");
        EchoURLConnection connection=(EchoURLConnection) url.openConnection();
        //�����������
        connection.setDoOutput(true);
        return connection;
    }
    
    private void ouputDataToService(EchoURLConnection con) throws IOException{
        //��������
        PrintWriter writer=new PrintWriter(con.getOutputStream(),true);
        while(true){
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=bufferedReader.readLine();
            writer.write(msg);
            //��ȡ���������ص���Ϣ
            String echoMsg=(String) con.getContent();
            System.out.println(echoMsg);
            if(echoMsg.equals("echo:bye")){
                con.disconnect();
                break;
            }
        }
    }
}
