package top.kly.qqclient.service;

import top.kly.qqcommon.Message;
import top.kly.qqcommon.MessageType;
import top.kly.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 该类完成用户登录验证和用户注册等功能
 */
public class UserClientService {
    private User u = new User();
    // Socket在其他地方也可能使用，因此做成一个成员属性
    private Socket socket;

    /**
     * 检查登录是否成功
     * @param userId 用户名
     * @param pwd 密码
     * @return 登录是否成功
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean checkUser(String userId, String pwd) throws IOException, ClassNotFoundException {
        boolean flag = false;
        // 创建User对象
        u.setUserId(userId);
        u.setPasswd(pwd);
        // 链接到服务端，发送User对象
        socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
        // 得到ObjectOutputStream对象
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        // 发送User对象给服务端
        oos.writeObject(u);
        // 读取从服务端回复的Message对象
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message ms = (Message) ois.readObject();
        // 判断是否登录成功
        if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
            // 创建一个和服务器端保持通信的线程
            ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
            // 启动客户端的线程
            ccst.start();
            // 为了和服务端支持链接多个线程，采用集合进行管理线程
            ManageClientConnectServerThread.addClientConnectServerThread(userId,ccst);
            // 登录成功的标记
            flag = true;
        } else {
            // 登录失败了，我们就不能启动和服务器通信的线程，关闭socket
            socket.close();
        }
        return flag;
    }

    /**
     * 向服务端请求在线用户列表
     */
    public void onlineFriendList() throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                getClientConnectServerThread(u.getUserId())
                .getSocket().getOutputStream());
        oos.writeObject(message);
    }

    /**
     * 退出客户端，并给服务端发送一个退出系统的message对象
     * 对应用户下线
     */
    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());
        // 发送message
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(u.getUserId()).getSocket()
                    .getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId() + "下线了...");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
