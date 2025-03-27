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
     * Loads content from the associated file into this chapter
     *
     * @throws ChapterContentException If content cannot be loaded
     */
    public void loadContent() throws ChapterContentException {
        if (getFile() != null && !getFile().isEmpty()) {
            try {
                this.contentText = fileManager.readTextFile(getFile());
            } catch (IOException e) {
                throw new ChapterContentException("Failed to load chapter content: " + e.getMessage(), e);
            }
        } else {
            throw new ChapterContentException("No file path specified for chapter.");
        }
    }

    /**
     * Saves the chapter content to its associated file
     *
     * @throws FileOperationException If the content cannot be saved to file
     */
    public void saveContent() throws FileOperationException {
        if (getFile() != null && !getFile().isEmpty() && contentText != null) {
            try {
                fileManager.writeTextFile(getFile(), contentText);
            } catch (IOException e) {
                throw new FileOperationException("Failed to save chapter content: " + e.getMessage(), e);
            }
        } else {
            throw new FileOperationException("Missing file path or content for chapter.");
        }
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
            // Show just the first 100 characters as preview
            String preview = contentText.length() > 100
                    ? contentText.substring(0, 100) + "..."
                    : contentText;
            System.out.println(preview);
        } else {
            System.out.println("Content not loaded. Please load content first.");
        }
    }
}