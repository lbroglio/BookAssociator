package com.lukebroglio.BookAssociation;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
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
    //This is necessary for interacting with the JSON files.
    @SuppressWarnings("unchecked")
    public void readInBooks(String filePath) throws FileNotFoundException, ParseException {
        //Loads the entire JSON file into a string
        String booksJson = new Scanner(new File(filePath)).useDelimiter("\\Z").next();

        //Parse the read JSON
        JSONParser parser = new JSONParser(booksJson);
        ArrayList<Object> parsedArr = parser.parseArray();

        //Get the books array from the parsed JSON
        ArrayList<Object> booksArr = (ArrayList<Object>) parsedArr.get(0);

        //Parse the Map holding the weight of the tags
        HashMap<String,HashMap<String, BigInteger>> tagWeight = (HashMap<String, HashMap<String, BigInteger>>) parsedArr.get(1);

        //Load the books into the storage map associated with their titles
        for (Object o : booksArr) {
            Book tmp = new Book((LinkedHashMap<Object, Object>) o,tagWeight);
            bookMap.put(tmp.getTitle(),tmp);
        }
    }

    /**
     * Writes the Books currently stored by this object to a specified file in  JSON format
     * @param saveFile Path to the file to write the JSON to
     * @throws IOException Thrown if the file could be opened
     */
    public void saveBooks(String saveFile) throws IOException {
        //Holds the JSON Object Books
        JSONArray bookJson = new JSONArray();
        //Standard Array of all the books in this object
        Book[] bookArray = bookMap.values().toArray(new Book[0]);
        //Associates each books with a Map storing the weight of its tags
        HashMap<String,HashMap<String,Integer>> tagWeights = new HashMap<>();

        //For every book
        for(int i=0; i < bookMap.size(); i++){
            //Add the Book to its array
            bookJson.put(new JSONObject(bookArray[i]));
            //Put an entry to store its tags weight
            tagWeights.put(bookArray[i].getTitle(),new HashMap<>());

            //For every tag
            WeightedList<String> currTags = bookArray[i].getTags();

            for(int j =0; j <currTags.size(); j++){
                //Associate the tag with its weight
                tagWeights.get(bookArray[i].getTitle()).put(currTags.get(j),currTags.getWeight(j));
            }
        }
        //Add the Book array and Tag Map to a JSON array
        JSONArray combinedJSON = new JSONArray();
        combinedJSON.put(bookJson);
        combinedJSON.put(new JSONObject(tagWeights));

        //Write the JSON to a file
        FileWriter outputFile = new FileWriter(saveFile);
        outputFile.write(combinedJSON.toString());

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
            int similarityRank = startingPoint.getSimilarityRanking(allBooks[i]);
            if(similarityRank != 0 && !allBooks[i].equals(startingPoint)){
                relatedBooks.addWithWeight(allBooks[i], similarityRank);
            }

        }

        return relatedBooks;
    }

    /**
     * Gets a Book with the given title from the map
     *
     * @param title String storing the title of the book to get
     * @return The {@link Book} object of the requested Book
     */
    public Book getBook(String title){
        return bookMap.get(title);
    }

    public Book[] getAllBooks(){
        return bookMap.values().toArray(new Book[0]);
    }

    public void tagBook(String title, String tag){
        bookMap.get(title).addTag(tag);
    }

}
