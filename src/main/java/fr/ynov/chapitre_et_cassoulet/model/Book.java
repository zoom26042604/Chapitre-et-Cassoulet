package main.java.fr.ynov.chapitre_et_cassoulet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract base class representing a book in the library system.
 * Contains common properties and behaviors shared by all types of books.
 */
public abstract class Book {
    private int id;
    private String title;
    private String description;
    private String coverImagePath;
    private String artist;
    private final List<String> genres;
    private final List<String> tags;
    private Date dateAdded;
    private String status;
    private int view;
    private final List<Chapter> chapters;

    /**
     * Default constructor initializing collections and default values
     */
    protected Book() {
        this.genres = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.chapters = new ArrayList<>();
        this.dateAdded = new Date();
        this.view = 0;
    }

    /**
     * Parameterized constructor for creating a book with basic information
     *
     * @param id The unique identifier for the book
     * @param title The title of the book
     * @param description Brief description of the book
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
     * @param id The new ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the book's title
     *
     * @return The title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the book's title
     *
     * @param title The new title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the book's description
     *
     * @return The description of the book
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the book's description
     *
     * @param description The new description to set
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
     * @param coverImagePath The new cover image path to set
     */
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    /**
     * Gets the artist or author of the book
     *
     * @return The artist's name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the artist or author of the book
     *
     * @param artist The artist's name to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gets the list of genres associated with the book
     *
     * @return The list of genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Gets the list of tags associated with the book
     *
     * @return The list of tags
     */
    public List<String> getTags() {
        return tags;
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
     * Gets the current publication status of the book
     *
     * @return The status string
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the publication status of the book
     *
     * @param status The status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the view count of the book
     *
     * @return The number of views
     */
    public int getView() {
        return view;
    }

    /**
     * Sets the view count of the book
     *
     * @param view The view count to set
     */
    public void setView(int view) {
        this.view = view;
    }

    /**
     * Gets the list of chapters in the book
     *
     * @return The list of chapters
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
     * Adds a genre to the book if not already present
     *
     * @param genre The genre to add
     */
    public void addGenre(String genre) {
        if (!this.genres.contains(genre)) {
            this.genres.add(genre);
        }
    }


    /**
     * Adds a tag to the book if not already present
     *
     * @param tag The tag to add
     */
    public void addTag(String tag) {
        if (!this.tags.contains(tag)) {
            this.tags.add(tag);
        }
    }

    /**
     * Increments the view count for this book
     */
    public void incrementView() {
        this.view++;
    }

    /**
     * Gets a chapter by its order number
     *
     * @param number The chapter number to retrieve
     * @return The chapter with the specified number, or null if not found
     */
    public Chapter getChapterByNumber(int number) {
        return chapters.stream()
                .filter(chapter -> chapter.getNumOrder() == number)
                .findFirst()
                .orElse(null);
    }

    /**
     * Displays detailed information about the book
     */
    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author/Artist: " + artist);
        System.out.println("Description: " + description);
        System.out.println("Status: " + status);
        System.out.println("Genres: " + String.join(", ", genres));
        System.out.println("Tags: " + String.join(", ", tags));
        System.out.println("Total chapters: " + chapters.size());
        System.out.println("Total views: " + view);
    }

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

