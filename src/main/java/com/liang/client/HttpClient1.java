package com.liang.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient1 {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://vip.com");
            // URL.setURLStreamHandlerFactory(fac);
            // 接受响应结果
            InputStream in = url.openConnection().getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = in.read(buff)) != -1) {
                buffer.write(buff, 0, len);
            }
            System.out.println(buffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
