package top.kly.qqclient.service;

import top.kly.qqcommon.Message;
import top.kly.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 该类提供和消息相关的服务方法
 */
public class MessageClientService {
    /**
     * 发送消息
     *
     * @param content  消息内容
     * @param senderId 发送用户ID
     * @param getterId 接收用户ID
     */
    public void sendMessageToOne(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        // 配置普通聊天消息
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        System.out.println(senderId + "对" + getterId + "说：" + content);
        // 发送给服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(senderId).
                    getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 群发消息
     * @param content 内容
     * @param senderId 发送者ID
     */
    public void sendMessageToAll(String content, String senderId) {
        Message message = new Message();
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        // 配置群发消息
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        System.out.println(senderId + "对大家说：" + content);
        // 发送给服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(senderId).
                    getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
