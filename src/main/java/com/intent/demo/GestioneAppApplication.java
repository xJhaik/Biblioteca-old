package com.intent.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories(basePackages = "com.intent.demo.repository")
@SpringBootApplication
@EnableSwagger2
public class GestioneAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestioneAppApplication.class, args);
	}

}
