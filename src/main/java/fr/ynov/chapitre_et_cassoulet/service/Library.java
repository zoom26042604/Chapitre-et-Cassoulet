package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.BookNotFoundException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Chapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manages the library's catalogue of books and provides operations for searching,
 * adding, and retrieving books.
 */
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Book> catalogue;

    /**
     * Default constructor initializing an empty catalogue
     */
    public Library() {
        this.catalogue = new ArrayList<>();
    }

    /**
     * Gets the entire book catalogue
     *
     * @return The list of all books in the library
     */
    public List<Book> getCatalogue() {
        return catalogue;
    }

    /**
     * Sets the library's catalogue
     * This method is deprecated in favor of directly adding books
     *
     * @param catalogue The new catalogue to set
     */
    @Deprecated
    public void setCatalogue(List<Book> catalogue) {
        if (catalogue != null) {
            this.catalogue = catalogue;
        }
    }

    /**
     * Adds a book to the catalogue
     *
     * @param book The book to add
     * @return true if the book was added, false otherwise
     */
    public boolean addBook(Book book) {
        if (book != null) {
            return catalogue.add(book);
        }
        return false;
    }

    /**
     * Removes a book from the catalogue
     *
     * @param book The book to remove
     * @return true if the book was removed, false otherwise
     */
    public boolean removeBook(Book book) {
        if (book != null) {
            return catalogue.remove(book);
        }
        return false;
    }

    /**
     * Gets a book by its ID
     *
     * @param id The ID of the book to find
     * @return The found book
     * @throws BookNotFoundException If no book with the specified ID is found
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

    /**
     * Finds a chapter in any book by its ID
     *
     * @param chapterId The ID of the chapter to find
     * @return The book and chapter pair if found
     * @throws BookNotFoundException If the chapter isn't found in any book
     */
    public Object[] findChapterById(int chapterId) throws BookNotFoundException {
        for (Book book : catalogue) {
            for (Chapter chapter : book.getChapters()) {
                if (chapter.getId() == chapterId) {
                    return new Object[]{book, chapter};
                }
            }
        }
        throw new BookNotFoundException("Chapter with ID " + chapterId + " not found in any book");
    }
}