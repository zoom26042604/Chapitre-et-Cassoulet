package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.BookNotFoundException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a library that manages a collection of books.
 * Provides methods for adding, retrieving, and managing books in the catalogue.
 */
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Book> catalogue;
    private int nextBookId;

    /**
     * Default constructor initializing an empty catalogue
     */
    public Library() {
        this.catalogue = new ArrayList<>();
        this.nextBookId = 1;
    }

    /**
     * Gets the complete catalogue of books
     *
     * @return List of all books in the library
     */
    public List<Book> getCatalogue() {
        return catalogue;
    }

    /**
     * Sets the catalogue of books (use with caution)
     *
     * @param catalogue The new catalogue to set
     * @deprecated Use addBook instead to maintain proper ID assignment
     */
    public void setCatalogue(List<Book> catalogue) {
        this.catalogue.clear();
        if (catalogue != null) {
            this.catalogue.addAll(catalogue);
        }
    }

    /**
     * Adds a book to the library, assigning an ID if needed
     *
     * @param book The book to add to the library
     */
    public void addBook(Book book) {
        if (book.getId() == 0) {
            book.setId(nextBookId++);
        } else {
            nextBookId = Math.max(nextBookId, book.getId() + 1);
        }
        catalogue.add(book);
    }

    /**
     * Retrieves a book by its ID
     *
     * @param id ID of the book to retrieve
     * @return The book with the specified ID
     * @throws BookNotFoundException If no book with the specified ID exists
     */
    public Book getBookById(int id) throws BookNotFoundException {
        return catalogue.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }



    /**
     * Gets a set of all genres used across all books
     *
     * @return Set of all unique genres in the library
     */
    public Set<String> getAllGenres() {
        return catalogue.stream()
                .flatMap(book -> book.getGenres().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Gets a set of all tags used across all books
     *
     * @return Set of all unique tags in the library
     */
    public Set<String> getAllTags() {
        return catalogue.stream()
                .flatMap(book -> book.getTags().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Gets a set of all statuses used across all books
     *
     * @return Set of all unique statuses in the library
     */
    public Set<String> getAllStatuses() {
        return catalogue.stream()
                .map(Book::getStatus)
                .collect(Collectors.toSet());
    }

    /**
     * Gets the total number of books in the library
     *
     * @return The number of books in the catalogue
     */
    public int getTotalBooks() {
        return catalogue.size();
    }
}