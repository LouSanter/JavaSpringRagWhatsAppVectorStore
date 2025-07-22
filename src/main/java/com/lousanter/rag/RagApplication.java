package com.lousanter.rag;

import com.lousanter.rag.ragService.RagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RagApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RagApplication.class, args);

		RagService ragService = context.getBean(RagService.class);
		ragService.indexarTodo();

	}

}
