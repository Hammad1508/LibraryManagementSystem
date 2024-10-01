package com.hmd.useractivityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UseractivityserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UseractivityserviceApplication.class, args);
	}

}
