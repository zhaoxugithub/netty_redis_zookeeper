package com.crazymakercircle.iodemo.OIO;

import com.crazymakercircle.NioDemoConfig;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client7000 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 70000; i++) {

            Socket socket = new Socket("localhost", NioDemoConfig.SOCKET_SERVER_PORT);

            if (socket != null) {
                System.out.println("客户端连接服务器成功！");
            }

            while (true) {
                System.out.println("请输入要发送的字符串：");
                String str = sc.next();
                socket.getOutputStream().write(str.getBytes());
            }
        }
    }
}