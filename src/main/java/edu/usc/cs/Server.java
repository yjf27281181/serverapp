package edu.usc.cs;

import java.io.*;
import java.net.Socket;

/**
 * @author yanjuefei
 * @date 2020/3/6 0006
 * @Description
 */
public class Server {

    public static final String IP_ADDR = "192.168.0.200";//服务器地址
    public static final int PORT = 1234;//服务器端口号

    public void start() {
        System.out.println("server start...");
        System.out.println("where receive \"OK\" from p2, terminate this program\n");
        while (true) {
            Socket socket = null;

            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(IP_ADDR, PORT);
                //读取服务器端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.print("input: \t");
                while(true) {
                    String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    out.writeUTF(str);

                    String ret = input.readUTF();
                    System.out.println("message from p2: " + ret);
                    if ("OK".equals(ret)) {
                        System.out.println("close connection with p2");
                        Thread.sleep(500);
                        break;
                    }
                }

                // 如接收到 "OK" 则断开连接


                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("app exception:" + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                        System.out.println("socket is closed");
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("app finally exception:" + e.getMessage());
                    }
                }
            }
        }

    }
}
