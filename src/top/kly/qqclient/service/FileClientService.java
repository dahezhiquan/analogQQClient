package top.kly.qqclient.service;

import top.kly.qqcommon.Message;
import top.kly.qqcommon.MessageType;

import java.io.*;

/**
 * 该类完成文件的传输服务
 */
public class FileClientService {
    /**
     * 文件传输
     * @param src 源文件地址
     * @param dest 目标地址
     * @param senderId 发送者ID
     * @param getterId 接收者ID
     */
    public void sendFileToOne(String src, String dest, String senderId, String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);

        // 读取文件
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int)new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);
            message.setFileBytes(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\n" + senderId + "给" + getterId + "发送文件" +
                src + "到对方电脑的目录" + dest);

        // 开始发送
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
                    .getClientConnectServerThread(senderId)
                    .getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
