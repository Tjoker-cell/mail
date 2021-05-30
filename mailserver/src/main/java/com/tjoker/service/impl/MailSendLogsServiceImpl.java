package com.tjoker.service.impl;

import com.tjoker.mapper.MailSendLogsMapper;
import com.tjoker.model.MailSendLogs;
import com.tjoker.service.MailSendLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @program: Elect
 * @description:
 * @author: 十字街头的守候
 * @create: 2021-05-25 22:16
 **/
@Service
@Slf4j
public class MailSendLogsServiceImpl implements MailSendLogsService{
    @Autowired
    private MailSendLogsMapper mailSendLogsMapper;

    @Override
    public Integer insert(MailSendLogs mailSendLogs) {return mailSendLogsMapper.insert(mailSendLogs); }

    @Override
    public int updateStatus(String msgId, int status) {
     return mailSendLogsMapper.updateStatus(msgId,status);
    }

    @Override
    public int updateCount(String msgID, Date date) {
        return mailSendLogsMapper.updateCount(msgID,date);
    }

    @Override
    public List<MailSendLogs> getMailSendLogsByStatus() {
        return mailSendLogsMapper.getMailSendLogsByStatus();
    }

}
