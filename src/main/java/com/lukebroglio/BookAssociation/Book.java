package com.lukebroglio.BookAssociation;


import java.util.*;
import java.time.LocalDate;


public class Book {
    /**
     * The title of this book
     */
    private final String title;
    /**
     * List storing the authors of this book.
     */
    private final ArrayList<Author> authors;
    /**
     * This book's publisher;
     */
    private String publisher;
    /**
     * The data this book was published (No differentiation is made between different editions or versions of books
     * so this will always be the date the very first edition or version was published)
     */
    private LocalDate publicationDate;
    /**
     * Stores the tags assigned to this book in order by an assigned weight using a {@link WeightedList}
     */
    private final WeightedList<String> tags;

    /**
     * Creates a new Book.
     *
     * @param title The title of this book
     * @param publisher The publisher (publishing company) of this book
     * @param publicationDate The date the first edition of this book was published.
     * no differentiation is made between the different versions or editions of books so this should be the publication date
     * of the earliest version or first edition.
     * @param authors {@link Collection} holding all the authors of the Book.
     */
    Book(String title, String publisher, LocalDate publicationDate, Collection<Author> authors){
        this.title = title;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.authors = new ArrayList<>();
        this.authors.addAll(authors);
        this.tags = new WeightedList<>();

    }

    /**
     * Creates a new Book.
     *
     * @param title The title of this book
     * @param publisher The publisher (publishing company) of this book
     * @param publicationDate The date the first edition of this book was published.
     * no differentiation is made between the different versions or editions of books so this should be the publication date
     * of the earliest version or first edition.
     * @param author The author of this book.
     */
    Book(String title, String publisher, LocalDate publicationDate, Author author){
        this.title = title;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.authors = new ArrayList<>();
        this.authors.add(author);
        this.tags = new WeightedList<>();
    }

    /**
     *Creates a Book with from a LinkedHashMap with all of its fields. Used to read in saved Books from a JSON file
     *
     * @param o The LinkedHashMap with the fields of this book
     */
    //This is necessary because this constructor is used when creating Books from a read in JSON which leads to unavoidable casts
    @SuppressWarnings("unchecked")
    Book(LinkedHashMap<Object,Object> o){
        this.title = (String) o.get("title");
        this.publisher = (String) o.get("publisher");;
        this.publicationDate = LocalDate.parse((String) o.get("publicationDate"));
        this.authors = new ArrayList<>();
        this.tags = new WeightedList<>();

        ArrayList<Object> authorList = (ArrayList<Object>) o.get("authors");

        for (Object a : authorList) {
            LinkedHashMap<Object,Object> linkedHashMap = (LinkedHashMap<Object,Object>) a;
            this.authors.add(new Author((String) linkedHashMap.get("firstName"), (String) linkedHashMap.get("lastName"), (String) linkedHashMap.get("middleName"), (String) linkedHashMap.get("title"), (String) linkedHashMap.get("suffix")));
        }

        ArrayList<String> tagList = (ArrayList<String>) o.get("tags");
        this.tags.addAll(tagList);

    }

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
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the date this book was published
     *
     * @param publicationDate The new publication data
     */
    public void setPublicationDate(LocalDate publicationDate) {
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

    /**
     * Checks to see if this book has the provided tag
     *
     * @param tag The tag to check to see it this book has
     *
     * @return True if the book has the tag false if it does not.
     */
   public boolean hasTag(String tag){
       return tags.contains(tag);
   }

    /**
     * Returns the number of times this Book has been given the given tag
     *
     * @param tag The tag to get the number of
     * @return The number of times the Book has been given the tag
     */
   public int tagNumber(String tag){
       return tags.getWeight(tags.indexOf(tag));
   }

    /**
     * Compares the tags of two Books and produces a new {@link WeightedList}  containing the tags they share.
     * The tags weight in the new list will be found by multiplying their current weights by each other.
     *
     * @param compareTo Another Book to compare the tags of two

     * @return {@link WeightedList}  containing the tags shared by this book and compareTo
     */
   public WeightedList<String> compareTags(Book compareTo){
       WeightedList<String> combinedTags = new WeightedList<String>();

       for(int i =0; i < tags.size(); i++){
            String currTag = tags.get(i);

            if(compareTo.hasTag(currTag)){
                int combinedWeight = tagNumber(currTag) * compareTo.tagNumber(currTag);
                combinedTags.addWithWeight(currTag,combinedWeight);
            }
       }

       return combinedTags;
   }

    /**
     * Returns the similarity ranking of two Books.
     * The similarity ranking is calculated by first comparing their tags ({@link #compareTags})
     * and then adding the weights of the combined tags together
     *
     * @param compareTo The Book to generate a similarity ranking to
     * @return The similarity ranking of this book with compareTo
     */
   public int getSimilarityRanking(Book compareTo){
       WeightedList<String> similarTags = compareTags(compareTo);

       int similarityRanking = 0;

       for(int i =0; i < similarTags.size(); i++){
           similarityRanking += similarTags.getWeight(i);
       }

       return similarityRanking;
   }


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", publicationDate=" + publicationDate +
                ", tags=" + tags +
                '}';
    }

    /**
     * Returns the similarity ranking of two Books.
     * The similarity ranking is calculated by first comparing their tags ({@link #compareTags})
     * and then adding the weights of the combined tags together
     *
     * @param a First book to get ranking off
     * @param b Second book to get ranking off
     *
     * @return The similarity ranking of this book with compareTo
     */
    public static int getSimilarityRanking(Book a, Book b){
        return a.getSimilarityRanking(b);
    }
}
