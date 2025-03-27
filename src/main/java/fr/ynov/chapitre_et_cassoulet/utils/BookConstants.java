package main.java.fr.ynov.chapitre_et_cassoulet.utils;

/**
 * Constants used throughout the application for book-related functionality
 */
public final class BookConstants {
    public static final String STATUS_ONGOING = "Ongoing";
    public static final String STATUS_COMPLETED = "Completed";
    public static final String STATUS_HIATUS = "Hiatus";

    public static final String TYPE_NOVEL = "Novel";
    public static final String TYPE_ROMAN = "Roman";

    public static final String DATA_DIR = "src/resources/data/";
    public static final String BOOKS_DIR = DATA_DIR + "books/";
    public static final String NOVEL_DIR = BOOKS_DIR + "novels/";
    public static final String ROMAN_DIR = BOOKS_DIR + "romans/";
    public static final String USERS_DIR = DATA_DIR + "users/";

    private BookConstants() {
        throw new AssertionError("Constants class should not be instantiated");
    }
}