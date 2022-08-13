package top.kly.qqclient.service;

import top.kly.qqcommon.Message;
import top.kly.qqcommon.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 客户端和服务端通信的线程类
 */
public class ClientConnectServerThread extends Thread{
    private Socket socket;
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("客户端的线程，等待读取从服务器端发送的消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n==========当前在线用户列表如下==========");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println(onlineUsers[i]);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    // 接受其他人发送的消息
                    System.out.println("\n" + message.getSender() + "对"
                     + message.getGetter() + "说：" + message.getContent() +
                            "\t时间：" + message.getSendTime());
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // 群发消息显示
                    System.out.println("\n" + message.getSender() + "对大家说：" +
                            message.getContent() + "\t时间" + message.getSendTime());
                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    // 发送文件消息
                    System.out.println("\n" + message.getSender() + "给" + message.getGetter()
                    + "发文件到" + message.getSrc() + "到我的电脑的目录" + message.getDest());
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n保存文件成功！");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
