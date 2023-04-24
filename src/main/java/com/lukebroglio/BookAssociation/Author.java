package com.lukebroglio.BookAssociation;

public class Author {
    /**
     * The first name of this author
     */
    private String firstName;
    /**
     * The last name of this author
     */
    private String lastName;
    /**
     * The middle name of this author. This will be blank by default
     */
    private String middleName;
    /**
     * Stores the title of this author if they have one. Dr., Captain, etc. This will be blank by default
     */
    private String title;
    /**
     * Stores the suffix of this author if they have one. SR. JR, PhD, etc. This will be blank by default
     */
    private String suffix;

    /**
     * Create a new author with all the information specified.
     * @param firstName The author's first name
     * @param lastName The author's last name
     * @param middleName The author's middle name or middle initial. Can be left blank by giving an empty string ("")
     * @param title The title of this author (Dr., Captain, etc). Can be left blank by giving an empty string ("")
     * @param suffix The suffix of this author (JR., PhD, etc). Can be left blank by giving an empty string ("")
     */
    public Author(String firstName, String lastName, String middleName, String title, String suffix) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.title = title;
        this.suffix = suffix;
    }

    /**
     * Create a new author with the given first and last name. All other fields will be sent to blank.
     * @param firstName The author's first name
     * @param lastName The author's last name
     */
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = "";
        this.title = "";
        this.suffix = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Gives the full name of this author
     * @return The authors first, middle if needed, and last name concatenated.
     */
    public String getFullName() {
        if (middleName.equals("") != true) {
            return firstName + " " + middleName + " " + lastName;
        } else {
            return firstName + " " + lastName;
        }
    }
}
