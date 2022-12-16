package com.zmh.mq.MQ;

import com.zmh.mq.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
/*@RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
@RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，根据接受的参数类型进入具体的方法中。*/
@RabbitListener(queues = "insert_queue")
public class InsertMq {

    @Autowired
    private UserMapper userMapper;

    @RabbitHandler
    public void process(String msg){
        //这里msg为数据库DDL语句
        userMapper.SQLinsert(msg);
        //为什么不使用mybatis-plus原因在于对于系统划分程度太高，如果这是对用户模块也就是对于用户表进行增删改操作，使用mybatis-plus处理更优秀，因为我们不需要写SQL语句使用m-p的方法，节省开发时间
        //但是如果我们处理高并发数据需要用到Redis作为缓存并且不仅仅只有一个模块比如说存在两个需要操作数据的模块，用户表，订单表
        //解决方法1：在RabbitMq中定义一个新的三个队列，之前三个队列分别处理用户增删改，新建的三个处理订单增删改
        //解决方法2：在增删改获得到数据需要变成JSON解析，{表：‘订单表’，数据：‘sql数据’}，通过表名判断到合适的逻辑里，不需要定义新的队列，但增加了程序的复杂度
        //综上，不难发现，我们想要实现不管怎么样都需要实体类和对应的mapper，这在大量需要Redis缓存的数据不友好，或增加队列或增加程序复杂度，都不可取
        //所以使用SQL语句来传递直接执行语句可能是最优解，缺点也就必须做Redis都要处理SQL语句编写，但也比前面要更实惠，insert，update，delete都是如此
        //studentMapper.insert(new Student());
    }

}
