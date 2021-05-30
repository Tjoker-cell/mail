# mail
基于rocketmq、redis搭建的独立邮件服务器，利用rocketmq消息队列的特性降低了邮件发送服务与系统其他模块之间的耦合性，同时利用rocketmq消息队列的特性达到异步发送邮件通知的效果，提升用户体验，利用redis天然的幂等性，保证了消息消息的幂等性。

#### 组织结构

```
mailserver
├── config -- redis配置
├── mapper -- 消息日志映射类
├── model --  模型层
├── receive -- 消息消息及邮件发送
├── service -- 业务处理层
├── task -- 定时任务
└── util -- redis、mq工具类
example 搭建mailserver的例子
```

#### 技术选型

| 技术       | 说明               |
| ---------- | ------------------ |
| rocketmq   | 邮件服务器核心     |
| redis      | 保证消息消息幂等性 |
| thymeleaf  | 模板引擎生成邮件   |
| springboot | 项目搭建框架       |
| mybatis    | ORM框架            |

#### 开发环境

| 工具       | 版本号 |
| ---------- | ------ |
| JDK        | 1.8    |
| Mysql      | 5.7    |
| Redis      | 5.0    |
| RocketMQ   | 4.5.2  |
| SpringBoot | 2.5    |
| Mybatis    | 2.1.1  |

#### 快速开始

> Windows环境部署

- 在本地项目中添加 `` mailserver `` 模块
- 将`mailserver`中的`application.properties`文件内容复制到本地项目中的` application.properties`中根据自己项目搭建环境修改相应配置
- 在需要发送邮件功能注入 `` RocketmqUtil``
- 调用`RocketmqUtil`中的 `recordLogs` 方法，将`MsgMQ`作为参数传递此方法中
- 注意：项目正常运行的前提是，项目环境已正确搭建并在配置文件中正确配置
- 如需更改邮件模板，请在`mail.html`、`MailServer`中进行相应修改
可以参考`example`模块进行使用

#### 学习本项目

[MailServer相关博客](https://blog.csdn.net/qq_43591899?spm=1001.2014.3001.5343)
