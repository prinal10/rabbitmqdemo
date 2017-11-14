package com.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class Config {

    @Autowired private TopicExchange topicExchange;
    @Autowired private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void sendData() {
        for(int i=1; i<11; i++) {
            rabbitTemplate.convertAndSend(topicExchange.getName(), "test.kgbhjbjhv1","This is the Data in the Queue : " + i);
        }
    }

    @RabbitListener(queues = "test-queue1")
    public void listener1(String data) {
        System.out.println("Consumer : 1  and the data is : " + data );
    }


    @RabbitListener(queues = "test-queue2")
    public void listener2(String data) {
        System.out.println("Consumer : 2  and the data is : " + data );
    }

}
