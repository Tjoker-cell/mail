package com.tjoker.service.impl;

import com.tjoker.mapper.UserMapper;
import com.tjoker.model.MsgMQ;
import com.tjoker.model.User;
import com.tjoker.service.UserService;
import com.tjoker.util.RocketmqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @program: tjoker
 * @description:模拟实现：用户输入密码错误次数过多，发送邮件提示用户账户异常
 * @author: 十字街头的守候
 * @create: 2021-05-25 22:16
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RocketmqUtil rocketmqUtil;

    @Override
    public String login(String username, String password) {
        UserMapper userMapper =new UserMapper();
      //模拟获得用户 登录错误次数
        User user = userMapper.login(username, password);
        //用户输入密码次数超过3次，发送邮寄通知用户账号异常
        if (user.getCount() >= 3) {
            //使用邮寄系统发送消息通知
            //封装msgmq消息
            MsgMQ msgMQ = new MsgMQ();
            String msgId = UUID.randomUUID().toString();
            msgMQ.setUsername(user.getName());
            msgMQ.setTarget_address(user.getEmail());
            msgMQ.setDate(new Date());
            msgMQ.setUserId(String.valueOf(user.getId()));//用户id转换为string
            msgMQ.setMsgId(msgId);
            rocketmqUtil.recordLogs(msgMQ);
            return "登录失败";
        }
        return "登录成功";
    }
}