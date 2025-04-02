package main.java.fr.ynov.chapitre_et_cassoulet.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a book in the library system.
 * Base class for all book types with common properties and behaviors.
 */
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private String coverImagePath;
    private String status;
    private String artist;
    private Date dateAdded;
    private Set<String> genres;
    private List<Chapter> chapters;

    /**
     * Default constructor initializes a book with default values
     */
    public Book() {
        this.genres = new HashSet<>();
        this.chapters = new ArrayList<>();
        this.dateAdded = new Date();
    }

    /**
     * Parameterized constructor for creating a book with basic information
     *
     * @param id The unique identifier for the book
     * @param title The title of the book
     * @param description A brief description of the book
     * @param coverImagePath Path to the book's cover image
     */
    public Book(int id, String title, String description, String coverImagePath) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImagePath = coverImagePath;
    }

    /**
     * Gets the unique identifier of the book
     *
     * @return The book's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the book
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the book
     *
     * @return The book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book
     *
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the book
     *
     * @return The book's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book
     *
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the path to the book's cover image
     *
     * @return Path to the cover image
     */
    public String getCoverImagePath() {
        return coverImagePath;
    }

    /**
     * Sets the path to the book's cover image
     *
     * @param coverImagePath The path to set
     */
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    /**
     * Gets the status of the book (ongoing, completed, etc.)
     *
     * @return The book's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the book
     *
     * @param status The status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the artist/author of the book
     *
     * @return The artist/author's name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the artist/author of the book
     *
     * @param artist The artist/author to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets the date when the book was added to the library
     *
     * @return The date added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the date when the book was added to the library
     *
     * @param dateAdded The date to set
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Gets the set of genres associated with the book
     *
     * @return Set of genres
     */
    public Set<String> getGenres() {
        return genres;
    }

    /**
     * Adds a genre to the book if not already present
     *
     * @param genre The genre to add
     */
    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    /**
     * Removes a genre from the book
     *
     * @param genre The genre to remove
     */
    public void removeGenre(String genre) {
        this.genres.remove(genre);
    }

    /**
     * Gets the list of chapters in the book
     *
     * @return List of chapters
     */
    public List<Chapter> getChapters() {
        return chapters;
    }

    /**
     * Adds a chapter to the book
     *
     * @param chapter The chapter to add
     */
    public void addChapter(Chapter chapter) {
        this.chapters.add(chapter);
    }

    /**
     * Gets the type of the book as a string
     *
     * @return The book's type
     */
    public String getType() {
        return this.getClass().getSimpleName();
    }

    /**
     * Displays detailed information about the book
     * Can be overridden by subclasses to add type-specific details
     */
    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("ID: " + id);
        System.out.println("Artist/Author: " + (artist != null ? artist : "Unknown"));
        System.out.println("Status: " + status);
        System.out.println("Description: " + (description != null ? description : "No description available"));
        System.out.println("Genres: " + String.join(", ", genres));
        System.out.println("Chapters: " + chapters.size());
    }
}