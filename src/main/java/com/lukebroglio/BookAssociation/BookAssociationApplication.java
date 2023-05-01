package com.lukebroglio.BookAssociation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class BookAssociationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookAssociationApplication.class, args);
	}

	@RequestMapping("/test")
	public String test(){
		return "Test Success";
	}



}
