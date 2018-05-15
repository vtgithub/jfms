package com.jfms.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
@EnableFeignClients
@EnableCircuitBreaker
//@EnableEurekaClient
//@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class EngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngineApplication.class, args);
	}
}
