package com.liang.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class shutdownAdminClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8001);
            // 发送关闭命令
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write("shutdown\r\n".getBytes());
            // 接受服务器反馈
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = null;
            while ((msg = reader.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
