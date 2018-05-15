package com.jfms.message_history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MessageHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageHistoryApplication.class, args);
	}
}
