package com.zmh.mq.MQ;

import com.zmh.mq.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "update_queue")
public class UpdateMq {

    @Autowired
    private UserMapper userMapper;

    @RabbitHandler
    public void process(String msg){
        userMapper.SQLupdate(msg);
    }

}
