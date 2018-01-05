package com.demo.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.List;

public class Receiver {

    private static final String BROKER_URI = "amqp://guest:guest@localhost:5671/";
    private final static String QUEUE_NAME = "jms/queue";
    private List<String> cache;

    public Receiver(List<String> cache) {
        this.cache = cache;
    }

    public void receive() throws Exception {
        // let's setup evrything and start listening
        ConnectionFactory factory = createConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, newConsumer(channel));
    }

    protected ConnectionFactory createConnectionFactory() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(BROKER_URI);
       //factory.useSslProtocol(); // Note this, we'll get back to it soon...
        return factory;
    }

    private DefaultConsumer newConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                cache.add(new String(body));  // put each message into the cache
            }
        };
    }
}