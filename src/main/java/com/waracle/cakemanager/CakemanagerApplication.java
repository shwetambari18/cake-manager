package com.waracle.cakemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class CakemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakemanagerApplication.class, args);
	}

}
