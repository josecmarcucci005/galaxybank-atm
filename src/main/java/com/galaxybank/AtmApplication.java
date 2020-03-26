package com.galaxybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

//@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan
public class AtmApplication { //extends SpringBootServletInitializer {

	/**
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AtmApplication.class);
	}
	*/

	public static void main(String[] args) {
		SpringApplication.run(AtmApplication.class, args);
	}

}
