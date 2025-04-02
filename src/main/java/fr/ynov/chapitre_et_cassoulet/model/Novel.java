package main.java.fr.ynov.chapitre_et_cassoulet.model;

import main.java.fr.ynov.chapitre_et_cassoulet.utils.BookConstants;

import java.io.Serial;

/**
 * Represents a novel type in the library.
 * Contains specific properties for novels such as origin and translator information.
 */
public class Novel extends Book {
    @Serial
    private static final long serialVersionUID = 1L;

    private String origin;
    private String translator;

    /**
     * Default constructor that initializes a novel with default values
     */
    public Novel() {
        super();
        this.setStatus(BookConstants.STATUS_ONGOING);
    }

    /**
     * Parameterized constructor for creating a novel with basic information
     *
     * @param id The unique identifier for the novel
     * @param title The title of the novel
     * @param description A brief description of the novel
     * @param coverImagePath Path to the novel's cover image
     */
    public Novel(int id, String title, String description, String coverImagePath) {
        super(id, title, description, coverImagePath);
        this.setStatus(BookConstants.STATUS_ONGOING);
    }

    /**
     * Gets the origin of the novel
     *
     * @return The origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin of the novel
     *
     * @param origin The origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets the translator of the novel
     *
     * @return The translator's name
     */
    public String getTranslator() {
        return translator;
    }

    /**
     * Sets the translator of the novel
     *
     * @param translator The translator's name to set
     */
    public void setTranslator(String translator) {
        this.translator = translator;
    }

    /**
     * Displays detailed information about the novel including type-specific details
     * Overrides the displayDetails method from the Book class
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: " + BookConstants.TYPE_NOVEL);
        System.out.println("Origin: " + (origin != null ? origin : "Unknown"));
        System.out.println("Translator: " + (translator != null ? translator : "None"));
    }
}