package com.hanghae99.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class springWeek03Team7 {

	public static void main(String[] args) {
		SpringApplication.run(springWeek03Team7.class, args);
	}

}
