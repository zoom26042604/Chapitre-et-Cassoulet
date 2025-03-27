package main.java.fr.ynov.chapitre_et_cassoulet.model;

import java.util.Date;

/**
 * Abstract class representing a chapter in a book.
 * Provides base functionality for all chapter types.
 */
public abstract class Chapter {
    private int id;
    private String title;
    private String file;
    private int numOrder;
    private Date dateAdded;
    private int view;

    /**
     * Default constructor initializing timestamps and view count
     */
    protected Chapter() {
        this.dateAdded = new Date();
        this.view = 0;
    }

    /**
     * Parameterized constructor for creating a chapter with basic information
     *
     * @param id The unique identifier for the chapter
     * @param title The title of the chapter
     * @param numOrder The order number/position of the chapter in the book
     */
    protected Chapter(int id, String title, int numOrder) {
        this();
        this.id = id;
        this.title = title;
        this.numOrder = numOrder;
    }

    /**
     * Gets the chapter's unique identifier
     *
     * @return The chapter's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the chapter's unique identifier
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the chapter's title
     *
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the chapter's title
     *
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the file path associated with the chapter
     *
     * @return The file path
     */
    public String getFile() {
        return file;
    }

    /**
     * Sets the file path associated with the chapter
     *
     * @param file The file path to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Gets the order number of this chapter in its book
     *
     * @return The order number
     */
    public int getNumOrder() {
        return numOrder;
    }

    /**
     * Sets the order number of this chapter in its book
     *
     * @param numOrder The order number to set
     */
    public void setNumOrder(int numOrder) {
        this.numOrder = numOrder;
    }

    /**
     * Gets the date when this chapter was added
     *
     * @return The date added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the date when this chapter was added
     *
     * @param dateAdded The date to set
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Gets the view count for this chapter
     *
     * @return The number of views
     */
    public int getView() {
        return view;
    }

    /**
     * Sets the view count for this chapter
     *
     * @param view The view count to set
     */
    public void setView(int view) {
        this.view = view;
    }

    /**
     * Increments the view count for this chapter
     */
    public void incrementView() {
        this.view++;
    }

    /**
     * Abstract method to be implemented by subclasses to display chapter content
     */
    public abstract void displayContent();

    /**
     * Displays basic information about the chapter
     */
    public void display() {
        System.out.println("Chapter " + numOrder + ": " + title);
        System.out.println("Added on: " + dateAdded);
        System.out.println("Views: " + view);
    }

    @Override
    public String toString() {
        return "Chapter " + numOrder + ": " + title;
    }
}