package com.tjoker.model;

import lombok.Data;

import java.util.Date;
@Data
public class MailSendLogs {
    private String msgID;//消息id
    private String group_name;//生产者组名
    private String msg_topic;//消息主题
    private String msg_tag;//消息标签
    private long userID;//消息体，用户id
    private String msg_body;//消息体
    private Integer msg_status;  //0 消息投递中   1 投递成功   2投递失败
    private Integer count;//重试次数
    private Date tryTime;//重试时间
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
}


