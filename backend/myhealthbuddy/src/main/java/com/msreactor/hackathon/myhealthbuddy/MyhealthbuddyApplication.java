package com.msreactor.hackathon.myhealthbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MyhealthbuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyhealthbuddyApplication.class, args);
	}

}
