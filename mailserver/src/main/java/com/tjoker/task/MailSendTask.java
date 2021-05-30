package com.tjoker.task;

import com.tjoker.model.MailSendLogs;
import com.tjoker.service.MailSendLogsService;
import com.tjoker.util.RocketmqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @program: mail
 * @description: 消息发送定时器
 * 主要针对发送失败的消息进行重新发送
 * @author: 十字街头的守候
 * @create: 2021-05-29 15:15
 **/
@Component
@Slf4j
public class MailSendTask {
    @Autowired
    private MailSendLogsService mailSendLogsService;
    @Autowired
    RocketmqUtil rocketmqUtil;
    @Scheduled(cron = "${scheduled.tjoker.corn}")
    public void mailResendTask() {
        log.info("针对失败消息重新投递");
        // statues 0 消息投递中   1 投递成功   2投递失败
        List<MailSendLogs> logs = mailSendLogsService.getMailSendLogsByStatus();
        //判断当前结果集是否为空
        if(logs==null||logs.size()==0){
            return ;
        }
        //轮训消息
        logs.forEach(mailSendLogs -> {
            // 判断当前重复次数是否超过3次如果超过3次则认定为失败
            if(mailSendLogs.getCount()>=3){
               //设置发送失败
                 mailSendLogsService.updateStatus(mailSendLogs.getMsgID(),2);
            }else{
                //重新发送
                //更新重复次数和尝试时间
                mailSendLogsService.updateCount(mailSendLogs.getMsgID(),new Date());
                //尝试再次发送
                try {
                   rocketmqUtil.sendMessage(mailSendLogs.getMsg_topic(),
                            mailSendLogs.getMsg_tag(),
                            mailSendLogs.getMsgID(),
                            mailSendLogs.getMsg_body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
