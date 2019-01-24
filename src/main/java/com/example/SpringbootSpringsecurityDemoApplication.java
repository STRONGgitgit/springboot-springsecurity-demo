package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages ="com.example")
@SpringBootApplication
public class SpringbootSpringsecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSpringsecurityDemoApplication.class, args);
	}
}
