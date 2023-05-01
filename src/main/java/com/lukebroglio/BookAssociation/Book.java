package com.lukebroglio.BookAssociation;


import java.util.ArrayList;
import java.util.Date;

public class Book {
    /**
     * The title of this book
     */
    private String title;
    /**
     * List storing the authors of this book.
     */
    ArrayList<Author> authors;
    /**
     * This book's publisher;
     */
    private String publisher;
    /**
     * The data this book was published
     */
    private Date publicationDate;
    /**
     * Stores the tags assigned to this book in order by an assigned weight using a {@link WeightedList}
     */
    public WeightedList<String> tags;

}
