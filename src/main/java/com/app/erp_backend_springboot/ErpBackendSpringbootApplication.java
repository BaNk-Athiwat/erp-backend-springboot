package com.app.erp_backend_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootApplication
@RestController
public class ErpBackendSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpBackendSpringbootApplication.class, args);
	}

	@GetMapping("/hello")
	public String greeting(@RequestParam String param) {
		return "Hello World!";
	}
	

}
