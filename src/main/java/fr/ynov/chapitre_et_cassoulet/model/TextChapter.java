package main.java.fr.ynov.chapitre_et_cassoulet.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a text-based chapter in a book.
 * Contains the actual text content of the chapter.
 */
public class TextChapter extends Chapter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String contentText;

    /**
     * Default constructor
     */
    public TextChapter() {
        super();
    }

    /**
     * Constructor with essential chapter properties
     *
     * @param id Chapter identifier
     * @param title Chapter title
     * @param numOrder Chapter order number
     */
    public TextChapter(int id, String title, int numOrder) {
        super(id, title, numOrder);
    }

    /**
     * Gets the text content of this chapter
     *
     * @return The text content
     */
    public String getContentText() {
        return contentText;
    }

    /**
     * Sets the text content of this chapter
     *
     * @param contentText The text content to set
     */
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}