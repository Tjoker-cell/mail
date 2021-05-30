package com.tjoker.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @program: Elect
 * @description: 生成者发送消息模板
 * @author: 十字街头的守候
 * @create: 2021-05-25 22:45
 **/
@Data
@Validated //数据校验
public class MsgMQ {
    @NotNull(message = "消息id不能为空")
    private String msgId;
    private String userId;//用户id
    @NotNull(message = "用户名不能为空")
    private String username;//用户名
    @Email(message = "邮箱格式错误")
    private String target_address;//消息接收方邮箱地址
    private Date date;//数据异常时间
}
