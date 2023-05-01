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
     * The data this book was published (No differentiation is made between different editions or versions of books
     * so this will always be the date the very first edition or version was published)
     */
    private Date publicationDate;
    /**
     * Stores the tags assigned to this book in order by an assigned weight using a {@link WeightedList}
     */
    public WeightedList<String> tags;

    /**
     * @return The title of this book
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The list of this book's authors
     */
    public ArrayList<Author> getAuthors() {
        return authors;
    }

    /**
     * Adds new author to the list of this book's authors
     * @param toAdd An {@link Author} object to add to the list
     */
    public void addAuthor(Author toAdd){
        authors.add(toAdd);
    }

    /**
     * @return The publisher of this book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of this book
     * @param publisher The new publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return The date this book was first published (No differentiation is made between different editions or versions of books
     * so this will always be the date the very first edition or version was published)
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the date this book was published
     *
     * @param publicationDate The new publication data
     */
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * @return {@link WeightedList} of the tags this book has been assigned
     */
    public WeightedList<String> getTags() {
        return tags;
    }

    /**
     * Adds a tag to this book. If the tag has already been added its weight will be increased by one
     * @param tag The tag to add or to increase the weight of
     */
   public void addTag(String tag){
        tags.add(tag);
   }


}
