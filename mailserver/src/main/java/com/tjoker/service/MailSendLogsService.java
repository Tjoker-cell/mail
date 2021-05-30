package com.tjoker.service;

import com.tjoker.model.MailSendLogs;

import java.util.Date;
import java.util.List;

public interface MailSendLogsService {
    //插入
    Integer insert(MailSendLogs mailSendLogs);

    //更新消息状态
    int updateStatus(String msgId,int status);

    //查询所有投递失败消息
    List<MailSendLogs> getMailSendLogsByStatus();

    //更新发送次数
    int updateCount(String msgID, Date date);
}
