package main.java.fr.ynov.chapitre_et_cassoulet.model;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.ChapterContentException;
import main.java.fr.ynov.chapitre_et_cassoulet.exception.FileOperationException;
import main.java.fr.ynov.chapitre_et_cassoulet.service.FileManager;
import java.io.IOException;

/**
 * Represents a text-based chapter in a book.
 * Contains the actual text content of the chapter.
 */
public class TextChapter extends Chapter {
    private static final long serialVersionUID = 1L;

    private String contentText;
    private final transient FileManager fileManager;

    /**
     * Default constructor
     */
    public TextChapter() {
        super();
        fileManager = new FileManager();
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
        fileManager = new FileManager();
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

    /**
     * Displays the chapter content to the console
     * Implementation of the abstract method from Chapter class
     */
    @Override
    public void displayContent() {
        if (contentText != null && !contentText.isEmpty()) {
            System.out.println("\n--- Chapter Content ---\n");
            System.out.println(contentText);
            System.out.println("\n--- End of Chapter ---\n");
        } else {
            System.out.println("Content not loaded. Please load content first.");
        }
    }

    @Override
    public void display() {
        super.display();

        if (contentText != null && !contentText.isEmpty()) {
            System.out.println("\n--- Chapter Preview ---");
            String preview = contentText.length() > 100
                    ? contentText.substring(0, 100) + "..."
                    : contentText;
            System.out.println(preview);
        } else {
            System.out.println("Content not loaded. Please load content first.");
        }
    }
}