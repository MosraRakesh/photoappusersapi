package com.photoapp.api.users.photoappusersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhotoappusersapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoappusersapiApplication.class, args);
	}

}
