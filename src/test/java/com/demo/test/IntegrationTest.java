package com.demo.test;

import com.demo.config.Receiver;
import com.demo.config.Sender;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static final String FIRST = "first";
    private static final String SECOND = "second";
    private static final String THIRD = "third";

    private static BrokerManager brokerStarter;

    @BeforeClass
    public static void startup() throws Exception {
        brokerStarter = new BrokerManager();
        brokerStarter.startBroker();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        brokerStarter.stopBroker();
    }

    private List<String> cache = new ArrayList<String>();

    @Test
    public void cacheShouldContainThreeEntries_afterThreeReceivedMessages() throws Exception {
        Sender sender = new Sender();
        sender.sendMessage(FIRST);
        sender.sendMessage(SECOND);
        sender.sendMessage(THIRD);
        new Receiver(cache).receive();

        Thread.sleep(500); // This, of course can and should be replaced with something smarter
        List<String> cacheContent = cache;

        assertEquals(3, cacheContent.size());
        assertEquals(FIRST, cacheContent.get(0));
        assertEquals(SECOND, cacheContent.get(1));
        assertEquals(THIRD, cacheContent.get(2));
    }

}