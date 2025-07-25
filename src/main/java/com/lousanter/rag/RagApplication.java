package com.lousanter.rag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RagApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RagApplication.class, args);
	}

}
