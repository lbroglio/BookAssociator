package com.lukebroglio.BookAssociation;

import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;


@SpringBootApplication
@RestController
public class BookAssociationApplication {

	public static BookStorage storedBooks;
	public static void main(String[] args) throws IOException, ParseException {
		storedBooks = new BookStorage();
		storedBooks.readInBooks("Test.json");

		SpringApplication.run(BookAssociationApplication.class, args);
	}

	@RequestMapping("/test")
	public String test(){
		return "Test Success";
	}

	@RequestMapping("/")
	public String allBooks(){
		return (new JSONArray(storedBooks.getAllBooks()).toString());
	}

	@RequestMapping("/related/{book}")
	public String getRelated(@PathVariable String bookTitle){
		return (new JSONArray(storedBooks.getRelated(storedBooks.getBook(bookTitle))).toString());
	}


}
