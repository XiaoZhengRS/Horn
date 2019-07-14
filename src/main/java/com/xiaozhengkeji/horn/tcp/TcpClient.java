package com.xiaozhengkeji.horn.tcp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TcpClient {
    private Socket socket;
    private SocketAddress address;
    private String Emass;
    public TcpClient(int port, String mass){
        try {
            socket = new Socket();
            address = new InetSocketAddress("127.0.0.1",  port);
            socket.connect(address, 1000);
            Emass = mass;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void talk() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        socket.getOutputStream().write(Emass.getBytes("UTF-8"));
        //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
        socket.shutdownOutput();
        outputStream.close();
        socket.close();
    }

}
