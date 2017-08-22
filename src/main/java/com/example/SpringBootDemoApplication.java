package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableAutoConfiguration
@EnableCaching //is the indicator for enabling the caching mechanism in the application
public class SpringBootDemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		/**
		 * What happens in SpringApplication.run(SpringBootDemoApplication.class, args)?
		 * Sets up default configuration
		 * Starts Spring application context
		 * Perform class path scan
		 * starts tomcat server.
		 */
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
