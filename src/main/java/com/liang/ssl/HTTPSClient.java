package com.liang.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @ClassName: HTTPSClient
 * @Description:(安全协议ssl)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年4月10日
 */
public class HTTPSClient {
    private String host = "www.usps.com";
    private int port = 443;
    private SSLSocketFactory factory;
    private SSLSocket socket;

    public void createSocket() throws UnknownHostException, IOException {
        factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = (SSLSocket) factory.createSocket(host, port);
        String[] supported = socket.getSupportedCipherSuites();
        System.out.println(supported);
        socket.setEnabledCipherSuites(supported);
    }

    public void communication() throws IOException {
        StringBuffer sb = new StringBuffer("GET http://" + host + "/HTTP/1.1\r\n");
        sb.append("Host:" + host + "\r\n");
        sb.append("Accept:*/*\r\n");
        sb.append("\r\n");
        /* 发送http请求 */
        OutputStream output = socket.getOutputStream();
        output.write(sb.toString().getBytes());
        /* 接受响应结果 */
        InputStream socketInput = socket.getInputStream();
        // ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 10);
        // byte[] buff=new byte[1024];
        int len = -1;
        StringBuffer stbuf = new StringBuffer();
        while ((len = socketInput.read(buffer.array())) != -1) {
            buffer.flip();
            stbuf.append(new String(buffer.array()));
            buffer.compact();
        }
        System.out.println(stbuf);
        socket.close();
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        HTTPSClient client=new HTTPSClient();
        client.createSocket();
        client.communication();
    }
}
