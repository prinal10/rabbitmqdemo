package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;

public class SendMessage {
    public static Config config = new Config();
    public static void main(String[] args) {

        config.sendData();

    }
}
