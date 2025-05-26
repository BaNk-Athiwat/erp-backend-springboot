package com.app.erp_backend_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.app.erp_backend_springboot")
public class ErpBackendSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpBackendSpringbootApplication.class, args);
	}
}
