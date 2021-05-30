package com.tjoker.mapper;

import com.tjoker.model.User;

/**
 * @program: mail
 * @description:模拟用户登录持久层
 * @author: 十字街头的守候
 * @create: 2021-05-30 10:50
 **/
public class UserMapper {
    public User login(String username, String password){
        User user = new User();
        user.setId("00001");
        user.setEmail("123456789@qq.com");
        user.setName("tjoker");
        user.setCount(3);
        return user;
    }
}
