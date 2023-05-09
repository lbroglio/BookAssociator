package com.lukebroglio.BookAssociation;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Handles storing all the Books in the programs data while it is running. Responsible for handling the association \
 * of Books to each other. Also handles reading the List of Books in and out.
 */
public class BookStorage {
    /**
     * Storage for all the Books. Stores the {@link Book} objects associated with their title.
     */
    private final HashMap<String, Book> bookMap;

    BookStorage(){
        bookMap = new HashMap<>();
    }

    /**
     * Reads a List of {@link Book}s from a JSON file. Stores it in the bookMap
     *
     * @param filePath The path to the file to read from
     * @throws FileNotFoundException Throws if the given filePath is invalid
     * @throws ParseException        Thrown by the JSONParser
     */
    //This is necessary for interacting the JSON files.
    @SuppressWarnings("unchecked")
    public void readInBooks(String filePath) throws FileNotFoundException, ParseException {
        //Loads the entire JSON file into a string
        String booksJson = new Scanner(new File(filePath)).useDelimiter("\\Z").next();

        //Parse the read JSON
        JSONParser parser = new JSONParser(booksJson);
        ArrayList<Object> booksArr = parser.parseArray();

        //Load the books into the storage map associated with their titles
        for (Object o : booksArr) {
            Book tmp = new Book((LinkedHashMap<Object, Object>) o);
            bookMap.put(tmp.getTitle(),tmp);
        }
    }

    /**
     * Writes the Books currently stored by this object to a specified file in  JSON format
     * @param saveFile Path to the file to write the JSON to
     * @throws IOException Thrown if the file could be opened
     */
    public void saveBooks(String saveFile) throws IOException {
        JSONArray bookJson = new JSONArray();
        Book[] bookArray = bookMap.values().toArray(new Book[0]);

        for(int i=0; i < bookMap.size(); i++){
            bookJson.put(new JSONObject(bookArray[i]));
        }

        FileWriter outputFile = new FileWriter(saveFile);
        outputFile.write(bookJson.toString());
        outputFile.close();
    }

    /**
     * Adds a new {@link Book} to this Storage object's map.
     * @param toAdd The Book to add
     */
    public void addBook(Book toAdd){
        bookMap.put(toAdd.getTitle(),toAdd);
    }

    /**
     * Gets a {@link WeightedList} of Books related to this one. Books are weighted by their similarity ranking
     * (determined by {@link Book#getSimilarityRanking})
     *
     * @param startingPoint The {@link Book} to find books related to.
     * @return A list of Books ordered by their similarity ranking
     */
    public WeightedList<Book> getRelated(Book startingPoint) {
        Book[] allBooks = bookMap.values().toArray(new Book[0]);
        WeightedList<Book> relatedBooks = new WeightedList<>();

        for (int i = 0; i < bookMap.size(); i++) {
            relatedBooks.addWithWeight(allBooks[i], startingPoint.getSimilarityRanking(allBooks[i]));
        }

        return relatedBooks;
    }

}
