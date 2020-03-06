package com.redhat.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(scanBasePackages = { "org.amazon", "com.redhat" })
@SpringBootApplication(scanBasePackages = { "com.redhat" })
@EnableScheduling()
public class Application {

    public final static String EXCHANGE_NAME = "rhpam-exchange";
    public final static String QUEUE_ROUTINGKEY = "routingKey1-boot";
    public final static String QUEUE_NAME = "pam-rabbitmq-cheques-queue";


    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
