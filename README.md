# analogQQClient🤷‍♂️
模拟QQ的命令行即时通讯系统客户端内核，涉及多线程与网络编程

本项目无数据库服务，使用HashMap来模拟数据库

# 项目简介😶‍🌫️
网络socket开发的客户端内核，可支持的实现私聊，群聊，文件传输，接收服务端新闻/消息推送功能


# 目录简介🙌

## qqcommon
用户网络传输的对象实体信息
- Message.java 客户端与服务端通信时的消息对象
- MessageType.java 表示消息类型有那些。在接口中定义了一些常量，不同的常量的值表示不同的消息类型
- User.java 用户/客户的信息

## service
业务服务层
- ClientConnectServerThread.java 客户端和服务端通信的线程类
- FileClientService.java 该类完成文件的传输服务
- ManageClientConnectServerThread.java 管理客户端链接到服务器端的线程类
- MessageClientService.java 该类提供和消息相关的服务方法
- UserClientService.java 该类完成用户登录验证和用户注册等功能


## utils
工具类

## view
命令行界面，显示主菜单

# 使用方法🎈
运行 QQView.java 即可开启客户端，您可以在UserClientService中修改连接服务器端的信息

修改位置如下：
``` java
// 链接到服务端，发送User对象
socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
```

为了方便演示效果，您也可以同时开启多个客户端实例，[IDEA同时开始多个实例的方法](https://blog.csdn.net/Gherbirthday0916/article/details/126306652?spm=1001.2014.3001.5501):

启动客户端后，您可以享受私聊，群聊，文件传输，接收服务端新闻/消息推送功能的服务

客户端演示使用图示：



