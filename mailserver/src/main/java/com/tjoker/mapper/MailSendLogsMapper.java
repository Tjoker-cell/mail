package com.tjoker.mapper;

import com.tjoker.model.MailSendLogs;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MailSendLogsMapper {

    int insert(MailSendLogs record);

    List<MailSendLogs> getMailSendLogsByStatus();

    int updateStatus(@Param("msgid") String msgId, @Param("status") int status);

    int updateCount(@Param("msgid") String msgId, @Param("date") Date date);
}