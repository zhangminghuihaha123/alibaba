package com.zmh.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MqApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class);
    }
}
