package com.xiaozhengkeji.horn.tcp;

import com.xiaozhengkeji.horn.Tool;
import com.xiaozhengkeji.horn.data.Data;
import org.bukkit.Bukkit;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private ServerSocket server;
    //构造服务端

    public TcpServer(){
        try {
            server = new ServerSocket(Data.MyServer);
        } catch (IOException e) {

        }
    }

    //开始监控

    public void talk() throws IOException {
        System.out.println("监控端口：" + Data.MyServer);
        while (true) {
            Socket socket = server.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
            while ((len = inputStream.read(bytes)) != -1) {
                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            Tool.messALL(String.valueOf(sb));
            Bukkit.getLogger().info("收到消息:" + String.valueOf(sb));
            inputStream.close();
            socket.close();
        }

    }


}



