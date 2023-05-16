package com.lukebroglio.BookAssociation;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedHashMap;

@CrossOrigin(maxAge = 3600)
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
	public String getRelated(@PathVariable String book){
		return (new JSONArray(storedBooks.getRelated(storedBooks.getBook(book))).toString());
	}

	@PostMapping("/add")
	public void addBook(@RequestBody String newBook) throws IOException, ParseException {
		JSONParser parser = new JSONParser(newBook);
		Book toAdd = new Book((LinkedHashMap<Object, Object>) parser.parse(),new HashMap<>());
		storedBooks.addBook(toAdd);
		storedBooks.saveBooks("Test.json");
	}


}
