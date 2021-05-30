package com.tjoker.receive;


import com.alibaba.fastjson.JSON;
import com.tjoker.model.MsgMQ;
import com.tjoker.util.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @program: Elect
 * @description: 消息队列消费者
 * @author: 十字街头的守候
 * @create: 2021-05-26 20:24
 **/
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}",
        topic = "${mq.tjoker.topic}",
        selectorExpression = "${mq.tjoker.tag}")
public class MailServer implements RocketMQListener<MessageExt> {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    RedisUtil redisUtil;
    @SneakyThrows
    @Override
    public void onMessage(MessageExt message) {
        log.info("开始接收消息");
        MsgMQ msgMQ = JSON.parseObject(message.getBody(), MsgMQ.class);
        String msgId = msgMQ.getMsgId();
        log.info("dataName:"+ msgMQ.toString());
        //检查当前消息是否被消费，保证消息幂等性
        if(redisUtil.hHasKey(msgId,"mail_send")){
            log.warn(msgId + " 消息已被消费,无法消费");
            return;
        }
        //接收到消息发送邮件
            sendMail(msgMQ);
    }

    private void sendMail(MsgMQ msgMQ)  {
        //收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        Context context = new Context();
        context.setVariable("userName", msgMQ.getUsername());
        //渲染HTML字符串 mail为邮件格式
        String mail = templateEngine.process("mail", context);
        //针对消息的格式验证
        //在前端注册或者登录时系统已经验证过，格式都正确
        //现在这里不做处理，默认消息格式都正确
        try {
            helper.setFrom(mailProperties.getUsername());//发件人
            helper.setTo(msgMQ.getTarget_address());//收件人
            helper.setSubject("账户异常");//主题
            helper.setSentDate(new Date());//发送时间
            helper.setText(mail, true);// 正文
            javaMailSender.send(msg);//发送
            //将当前数据存入redis中
            redisUtil.hset(msgMQ.getMsgId(),"mail_send","tjoker");
            log.info(msgMQ.getMsgId()+" 邮件发送成功");
        } catch (MessagingException e) {
          //  e.printStackTrace();
            log.error("邮件发送失败"+e.getMessage());
        }
    }
}
