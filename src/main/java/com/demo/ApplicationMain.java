package com.demo;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.BindingFactoryBean;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ApplicationMain {

    @Autowired private ConnectionFactory connectionFactory;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);

    }


    @PostConstruct
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return  rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
    @Bean
    public MessageConverter messageConverter() {
        ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter(new
                Jackson2JsonMessageConverter());

        messageConverter.addDelegate(MessageProperties.CONTENT_TYPE_TEXT_PLAIN, new SimpleMessageConverter());

        return messageConverter;
    }

    @Bean
    public TopicExchange topicExchange() {
       return  new TopicExchange("testTopic");
    }

    @Bean
    public Queue queue1() {
        return new Queue("test-queue1");
    }
    @Bean
    public Queue queue2() {
        return new Queue("test-queue2");
    }

    @Bean
    public Binding binding1(TopicExchange topicExchange, Queue queue1) {
        return BindingBuilder.bind(queue1).to(topicExchange).with("test*");
    }
    @Bean
    public Binding binding2(TopicExchange topicExchange, Queue queue2) {
        return BindingBuilder.bind(queue2).to(topicExchange).with("test*");
    }

}
