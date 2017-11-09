package com.demo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitListenerDemo {

    @RabbitListener(queues = "test")
    public void testConsumer1(String data) {
        System.out.println("Consumer number - 1: " + data);
    }

    @RabbitListener(queues = "test")
    public void testConsumer2(String data) {
        System.out.println("Consumer number - 2: " + data);
    }
}
