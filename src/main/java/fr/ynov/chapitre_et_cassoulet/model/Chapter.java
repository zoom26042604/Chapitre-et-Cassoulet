package main.java.fr.ynov.chapitre_et_cassoulet.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a chapter in a book.
 * Base class for different types of chapters.
 */
public class Chapter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private int numOrder;

    /**
     * Default constructor
     */
    public Chapter() {
    }

    /**
     * Constructor with essential chapter properties
     *
     * @param id Chapter identifier
     * @param title Chapter title
     * @param numOrder Chapter order number
     */
    public Chapter(int id, String title, int numOrder) {
        this.id = id;
        this.title = title;
        this.numOrder = numOrder;
    }

    /**
     * Gets the unique identifier of the chapter
     *
     * @return The chapter's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the chapter
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the chapter
     *
     * @return The chapter's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the chapter
     *
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the order number of the chapter in the book
     *
     * @return The chapter's order number
     */
    public int getNumOrder() {
        return numOrder;
    }

    /**
     * Sets the order number of the chapter in the book
     *
     * @param numOrder The order number to set
     */
    public void setNumOrder(int numOrder) {
        this.numOrder = numOrder;
    }
}