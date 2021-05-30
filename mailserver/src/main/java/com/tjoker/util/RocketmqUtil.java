package com.tjoker.util;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.tjoker.model.MailSendLogs;
import com.tjoker.model.MsgMQ;
import com.tjoker.service.MailSendLogsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @program: mail
 * @description: rocketmq工具类（发送消息）
 * @author: 十字街头的守候
 * @create: 2021-05-29 21:35
 **/
@Component
@Slf4j
public class RocketmqUtil {
    @Autowired
    private  RocketMQTemplate rocketMQTemplate;
    @Autowired
    private  MailSendLogsService mailSendLogsService;
    @Value("${mq.tjoker.timeout}")
    private  long  timeout;
    @Value("${mq.tjoker.topic}")
    private String topic;
    @Value("${mq.tjoker.tag}")
    private String tag;
    @Value("${rocketmq.producer.group}")
    private String producer_group;
    @Value("${mq.tjoker.retrytime}")
    private long retrytime;
    /**
     * @Description:记录日志信息，保证rocketmq消息一定发送成功
     * @param msgMQ: 消息
     * @return: void
     */
    public void recordLogs(MsgMQ msgMQ){

        //将当前数据存入数据库
        MailSendLogs mailSendLogs= new MailSendLogs();
        mailSendLogs.setCreateTime(new Date());
        System.out.println("时间："+mailSendLogs.getCreateTime());
        mailSendLogs.setGroup_name(producer_group);
        mailSendLogs.setMsg_status(0);
        mailSendLogs.setMsg_tag(tag);
        mailSendLogs.setMsg_topic(topic);
        mailSendLogs.setCount(0);
        mailSendLogs.setTryTime(new Date(System.currentTimeMillis()+1000 * 60 *retrytime));
        mailSendLogs.setMsgID(msgMQ.getMsgId());
        mailSendLogs.setUserID(Integer.parseInt(msgMQ.getUserId()));
        mailSendLogs.setMsg_body(JSON.toJSONString(msgMQ));
        mailSendLogsService.insert(mailSendLogs);
        //异步发送消息
        try {
            sendMessage(topic,
                    tag,
                    msgMQ.getMsgId(),
                    JSON.toJSONString(msgMQ));
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("消息"+ msgMQ.getMsgId()+"发送失败");
        }
    }

    /**
     * @Description:消息发送
     * @param topic: 主题：一级目录
     * @param tags: 标签：二级目录
     * @param msgId: 消息id
     * @param body: 主体
     * @return: void
     */
    public  void sendMessage(String topic, String tags, String msgId, String body) throws Exception {
        //判断Topic是否为空
        if (StringUtils.isEmpty(topic)) {
            log.info("mail主题不能为空");
            return ;
        }
        //判断消息内容是否为空
        if (StringUtils.isEmpty(body)) {
            log.info("消息不能为空");
            return ;
        }
        //消息体
        //异步发送消息(destination：没有进行封装)
        rocketMQTemplate.asyncSend(topic + ":" + tags, body, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("消息发送成功"+sendResult.getSendStatus());
                //更新用户状态
                mailSendLogsService.updateStatus(msgId,1);
            }
            @Override
            public void onException(Throwable e) {
                //消息发送失败，定义一个定时器定时去发送消息
                e.printStackTrace();
                log.info("消息发送失败");

            }
        },timeout);

    }
}
