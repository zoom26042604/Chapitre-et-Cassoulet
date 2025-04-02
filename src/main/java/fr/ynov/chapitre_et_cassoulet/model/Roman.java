package main.java.fr.ynov.chapitre_et_cassoulet.model;

import main.java.fr.ynov.chapitre_et_cassoulet.utils.BookConstants;

import java.io.Serial;

/**
 * Represents a roman (comic book) type in the library.
 * Contains specific properties for roman such as series and illustrator information.
 */
public class Roman extends Book {
    @Serial
    private static final long serialVersionUID = 1L;

    private String series;
    private String illustrator;

    /**
     * Default constructor that initializes a roman with default values
     */
    public Roman() {
        super();
        this.setStatus(BookConstants.STATUS_ONGOING);
    }

    /**
     * Parameterized constructor for creating a roman with basic information
     *
     * @param id The unique identifier for the roman
     * @param title The title of the roman
     * @param description A brief description of the roman
     * @param coverImagePath Path to the roman's cover image
     */
    public Roman(int id, String title, String description, String coverImagePath) {
        super(id, title, description, coverImagePath);
        this.setStatus(BookConstants.STATUS_ONGOING);
    }

    /**
     * Gets the series name that this roman belongs to
     *
     * @return The series name
     */
    public String getSeries() {
        return series;
    }

    /**
     * Sets the series name that this roman belongs to
     *
     * @param series The series name to set
     */
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * Gets the illustrator of the roman
     *
     * @return The illustrator's name
     */
    public String getIllustrator() {
        return illustrator;
    }

    /**
     * Sets the illustrator of the roman
     *
     * @param illustrator The illustrator's name to set
     */
    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    /**
     * Displays detailed information about the roman including type-specific details
     * Overrides the displayDetails method from the Book class
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: " + BookConstants.TYPE_ROMAN);
        System.out.println("Series: " + (series != null ? series : "Standalone"));
        System.out.println("Illustrator: " + (illustrator != null ? illustrator : "Unknown"));
    }
}