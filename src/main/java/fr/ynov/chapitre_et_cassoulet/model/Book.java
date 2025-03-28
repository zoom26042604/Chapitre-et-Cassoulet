package main.java.fr.ynov.chapitre_et_cassoulet.model;

import main.java.fr.ynov.chapitre_et_cassoulet.utils.BookConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract class representing a book in the library.
 * Provides base functionality for all book types.
 */
public abstract class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String type;
    private String title;
    private String description;
    private String coverImagePath;
    private String artist;
    private String status;
    private Date dateAdded;
    private final Set<String> genres;
    private final Set<String> tags;
    private final List<Chapter> chapters;

    /**
     * Default constructor initializing collections and setting default values
     */
    protected Book() {
        this.genres = new HashSet<>();
        this.tags = new HashSet<>();
        this.chapters = new ArrayList<>();
        this.dateAdded = new Date();
        this.status = BookConstants.STATUS_ONGOING;
    }

    /**
     * Parameterized constructor for creating a book with basic information
     *
     * @param id The unique identifier for the book
     * @param title The title of the book
     * @param description A brief description of the book
     * @param coverImagePath Path to the book's cover image
     */
    protected Book(int id, String title, String description, String coverImagePath) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImagePath = coverImagePath;
    }

    /**
     * Gets the book's unique identifier
     *
     * @return The book's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the book's unique identifier
     *
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the book's title
     *
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the book's title
     *
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the book's description
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the book's description
     *
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the path to the book's cover image
     *
     * @return The cover image path
     */
    public String getCoverImagePath() {
        return coverImagePath;
    }

    /**
     * Sets the path to the book's cover image
     *
     * @param coverImagePath The cover image path to set
     */
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    /**
     * Gets the artist/author of the book
     *
     * @return The artist's name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the artist/author of the book
     *
     * @param artist The artist's name to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets the status of the book (ongoing, completed, etc)
     *
     * @return The status
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
     * Gets the date when this book was added
     *
     * @return The date added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Sets the date when this book was added
     *
     * @param dateAdded The date to set
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Gets the set of genres associated with this book
     *
     * @return The genres
     */
    public Set<String> getGenres() {
        return genres;
    }

    /**
     * Adds a genre to this book
     *
     * @param genre The genre to add
     */
    public void addGenre(String genre) {
        if (genre != null && !genre.isEmpty()) {
            genres.add(genre);
        }
    }

    /**
     * Removes a genre from this book
     *
     * @param genre The genre to remove
     */
    public void removeGenre(String genre) {
        genres.remove(genre);
    }

    /**
     * Gets the set of tags associated with this book
     *
     * @return The tags
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * Adds a tag to this book
     *
     * @param tag The tag to add
     */
    public void addTag(String tag) {
        if (tag != null && !tag.isEmpty()) {
            tags.add(tag);
        }
    }

    /**
     * Removes a tag from this book
     *
     * @param tag The tag to remove
     */
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    /**
     * Gets the list of chapters in this book
     *
     * @return The chapters
     */
    public List<Chapter> getChapters() {
        return chapters;
    }

    /**
     * Adds a chapter to this book
     *
     * @param chapter The chapter to add
     */
    public void addChapter(Chapter chapter) {
        if (chapter != null) {
            chapters.add(chapter);
        }
    }

    /**
     * Gets the book's explicit type
     *
     * @return The explicit type of the book
     */
    public String getType() {
        if (type != null) {
            return type;
        }
        return getClass().getSimpleName();
    }

    /**
     * Removes a chapter from this book
     *
     * @param chapter The chapter to remove
     * @return true if the chapter was removed, false otherwise
     */
    public boolean removeChapter(Chapter chapter) {
        return chapters.remove(chapter);
    }

    /**
     * Displays detailed information about the book
     */
    public void displayDetails() {
        System.out.println("Book Details:");
        System.out.println("Title: " + title);
        System.out.println("Artist/Author: " + (artist != null ? artist : "Unknown"));
        System.out.println("Status: " + status);
        System.out.println("Description: " + (description != null ? description : "No description available."));

        System.out.println("Genres: ");
        if (genres.isEmpty()) {
            System.out.println("  None");
        } else {
            for (String genre : genres) {
                System.out.println("  - " + genre);
            }
        }

        System.out.println("Chapters: " + chapters.size());
    }

    /**
     * Returns a string representation of the book
     *
     * @return String containing basic book information
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", chapters=" + chapters.size() +
                '}';
    }
}