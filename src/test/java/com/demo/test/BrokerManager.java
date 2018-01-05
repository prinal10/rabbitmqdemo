package com.demo.test;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;

public class BrokerManager {

    private static final String INITIAL_CONFIG_PATH = "target/test-classes/qpid-config.json";
    private static final String PORT = "5671";
    private final Broker broker = new Broker();

    public void startBroker() throws Exception {
        final BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", PORT);
        brokerOptions.setInitialConfigurationLocation(INITIAL_CONFIG_PATH);

        broker.startup(brokerOptions);
    }

    public void stopBroker() {
        broker.shutdown();
    }
}