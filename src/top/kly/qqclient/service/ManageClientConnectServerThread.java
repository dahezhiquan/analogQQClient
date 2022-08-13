package top.kly.qqclient.service;

import java.util.HashMap;

/**
 * 管理客户端链接到服务器端的线程类
 */
public class ManageClientConnectServerThread {
    // 将多个线程放入到HashMap集合进行管理
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    // 将某个线程加入到集合中
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    // 通过userId取出某个线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
