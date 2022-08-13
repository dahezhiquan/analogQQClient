package top.kly.qqclient.view;

import top.kly.qqclient.service.FileClientService;
import top.kly.qqclient.service.MessageClientService;
import top.kly.qqclient.service.UserClientService;
import top.kly.qqclient.utils.Utility;

import java.io.IOException;

/**
 * 显示主菜单
 */
public class QQView {
    // 是否显示菜单
    private boolean loop = true;
    // 用户输入
    private String key = "";
    private MessageClientService messageClientService = new MessageClientService();
    private UserClientService userClientService = new UserClientService();

    private FileClientService fileClientService = new FileClientService();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQView().mainMenu();
        System.out.println("退出系统！");
    }

    private void mainMenu() throws IOException, ClassNotFoundException {
        while (loop) {
            System.out.println("====================欢迎登录网络通信系统====================");
            System.out.println("\t\t1 登录系统🚗");
            System.out.println("\t\t9 退出系统❌");
            System.out.print("请输入你的选择：");

            key = Utility.readString(1);
            // 根据用户输入处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户名：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密 码：");
                    String pwd = Utility.readString(50);
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("==========欢迎" + userId + "==========");
                        while (loop) {
                            System.out.println("\n==========网络通信系统二级菜单（用户" + userId + ")==========");
                            System.out.println("\t\t 1 显示在线用户列表🚹");
                            System.out.println("\t\t 2 群发消息😀");
                            System.out.println("\t\t 3 私聊消息🦐");
                            System.out.println("\t\t 4 发送文件📕");
                            System.out.println("\t\t 9 退出系统❌");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    // 显示在线用户列表
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.print("请输入想对大家说的话：");
                                    String s = Utility.readString(100);
                                    messageClientService.sendMessageToAll(s, userId);
                                    break;
                                case "3":
                                    System.out.print("请输入想聊天的用户号（在线）：");
                                    String getterId = Utility.readString(50);
                                    System.out.print("请输入你想说的话：");
                                    String content = Utility.readString(100);
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    System.out.println("消息发送成功！");
                                    break;
                                case "4":
                                    System.out.print("请输入你想发送的用户：");
                                    getterId = Utility.readString(50);
                                    System.out.print("请输入你想发送文件的路径：");
                                    String src = Utility.readString(100);
                                    System.out.print("请输入你想把文件发送到对方的那个路径：");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9":
                                    // 退出客户端
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录失败！");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
