package com.gaoyifeng.IDaaS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IDaaSApplication {

    public static void main(String[] args) {
        SpringApplication.run(IDaaSApplication.class, args);
    }

}
