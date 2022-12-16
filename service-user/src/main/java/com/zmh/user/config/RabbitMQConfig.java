package com.zmh.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    /*交换机*/
    private String EXCHANGE_NAME = "/MYSQLMQ";


    private String INSERT_QUEUE = "insert_queue";

    private String UPDATE_QUEUE = "update_queue";

    private String DELETE_QUEUE = "delete_queue";


    /*注入队列和交换机到Spring容器中*/
    @Bean
    public Queue insertQueue(){
         return new Queue(INSERT_QUEUE,true);
    }
    @Bean
    public Queue updateQueue(){
        return new Queue(UPDATE_QUEUE,true);
    }
    @Bean
    public Queue deleteQueue(){
        return new Queue(DELETE_QUEUE,true);
    }
    @Bean
    public DirectExchange directExchange(){
        /*直连交换机*/
        return new DirectExchange(EXCHANGE_NAME);
    }


    /*关联交换机*/
    @Bean
    public Binding BindingInsertDirectExchange(){
        return BindingBuilder.bind(insertQueue()).to(directExchange()).with("INSERT");
    }
    @Bean
    public Binding BindingUpdateDirectExchange(){
        return BindingBuilder.bind(updateQueue()).to(directExchange()).with("UPDATE");
    }
    @Bean
    public Binding BindingDeleteDirectExchange(){
        return BindingBuilder.bind(deleteQueue()).to(directExchange()).with("DELETE");
    }


}
