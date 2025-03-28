package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.BookNotFoundException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages the library's book catalogue and provides methods
 * for adding, retrieving, and searching for books.
 */
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Book> catalogue;

    /**
     * Creates a new empty library
     */
    public Library() {
        catalogue = new ArrayList<>();
    }

    /**
     * Gets all books in the catalogue
     *
     * @return The full catalogue of books
     */
    public List<Book> getCatalogue() {
        return new ArrayList<>(catalogue);
    }

    /**
     * Adds a book to the catalogue
     *
     * @param book The book to add
     */
    public void addBook(Book book) {
        catalogue.add(book);
    }

    /**
     * Gets a specific book by ID
     *
     * @param id The ID of the book to retrieve
     * @return The book with the matching ID
     * @throws BookNotFoundException If no book with the given ID is found
     */
    public Book getBookById(int id) throws BookNotFoundException {
        for (Book book : catalogue) {
            if (book.getId() == id) {
                return book;
            }
        }
        throw new BookNotFoundException("Book with ID " + id + " not found");
    }

    /**
     * Gets all unique genres from all books in the catalogue
     *
     * @return A set of all genres
     */
    public Set<String> getAllGenres() {
        Set<String> allGenres = new HashSet<>();
        for (Book book : catalogue) {
            allGenres.addAll(book.getGenres());
        }
        return allGenres;
    }

    /**
     * Gets all unique statuses from all books in the catalogue
     *
     * @return A set of all statuses
     */
    public Set<String> getAllStatuses() {
        Set<String> allStatuses = new HashSet<>();
        for (Book book : catalogue) {
            allStatuses.add(book.getStatus());
        }
        return allStatuses;
    }

    /**
     * Searches for books matching the given criteria
     *
     * @param keyword The search keyword
     * @param genre The genre to filter by
     * @param status The status to filter by
     * @param type The book type to filter by
     * @return A list of matching books
     */
    public List<Book> searchBooks(String keyword, String genre, String status, String type) {
        return catalogue.stream()
                .filter(book -> isMatchingKeyword(book, keyword))
                .filter(book -> isMatchingGenre(book, genre))
                .filter(book -> isMatchingStatus(book, status))
                .filter(book -> isMatchingType(book, type))
                .collect(Collectors.toList());
    }

    private boolean isMatchingKeyword(Book book, String keyword) {
        if (keyword == null || keyword.isEmpty() || keyword.equalsIgnoreCase("All")) {
            return true;
        }
        String lowerKeyword = keyword.toLowerCase();
        return book.getTitle().toLowerCase().contains(lowerKeyword) ||
                (book.getDescription() != null && book.getDescription().toLowerCase().contains(lowerKeyword)) ||
                (book.getArtist() != null && book.getArtist().toLowerCase().contains(lowerKeyword));
    }

    private boolean isMatchingGenre(Book book, String genre) {
        return genre == null || genre.isEmpty() ||
                genre.equalsIgnoreCase("All Genres") ||
                book.getGenres().contains(genre);
    }

    private boolean isMatchingStatus(Book book, String status) {
        return status == null || status.isEmpty() ||
                status.equalsIgnoreCase("All Statuses") ||
                book.getStatus().equalsIgnoreCase(status);
    }

    private boolean isMatchingType(Book book, String type) {
        return type == null || type.isEmpty() ||
                type.equalsIgnoreCase("All Types") ||
                book.getClass().getSimpleName().equalsIgnoreCase(type);
    }

    /**
     * Gets the total number of books in the library
     *
     * @return The total number of books
     */
    public int getTotalBooks() {
        return catalogue.size();
    }
}