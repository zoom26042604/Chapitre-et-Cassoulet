package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.BookNotFoundException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Chapter;
import main.java.fr.ynov.chapitre_et_cassoulet.model.TextChapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class providing search and filtering capabilities for the library system.
 * Allows searching books by various criteria including title, genre, status and content.
 */
public class Search {
    private String keyword;
    private List<String> genres;
    private List<String> status;
    private String textBook;
    private final Library library;

    /**
     * Constructor that initializes a Search service with reference to a library
     *
     * @param library The library to search within
     */
    public Search(Library library) {
        this.library = library;
        this.genres = new ArrayList<>();
        this.status = new ArrayList<>();
    }

    /**
     * Gets the current search keyword
     *
     * @return The search keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Sets the search keyword
     *
     * @param keyword The keyword to search for
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Gets the list of genres used for filtering
     *
     * @return List of genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres used for filtering
     *
     * @param genres List of genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * Adds a genre to the filter if not already present
     *
     * @param genre Genre to add
     */
    public void addGenre(String genre) {
        if (!this.genres.contains(genre)) {
            this.genres.add(genre);
        }
    }

    /**
     * Clears all genre filters
     */
    public void clearGenres() {
        this.genres.clear();
    }

    /**
     * Gets the list of statuses used for filtering
     *
     * @return List of statuses
     */
    public List<String> getStatus() {
        return status;
    }

    /**
     * Sets the list of statuses used for filtering
     *
     * @param status List of statuses
     */
    public void setStatus(List<String> status) {
        this.status = status;
    }

    /**
     * Adds a status to the filter if not already present
     *
     * @param status Status to add
     */
    public void addStatus(String status) {
        if (!this.status.contains(status)) {
            this.status.add(status);
        }
    }

    /**
     * Clears all status filters
     */
    public void clearStatus() {
        this.status.clear();
    }

    /**
     * Gets the text content to search for within books
     *
     * @return Text content to search
     */
    public String getTextBook() {
        return textBook;
    }

    /**
     * Sets the text content to search for within books
     *
     * @param textBook Text content to search
     */
    public void setTextBook(String textBook) {
        this.textBook = textBook;
    }

    /**
     * Searches books by title
     *
     * @param title Title to search for
     * @return List of books matching the title
     */
    public List<Book> searchByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String searchTerm = title.toLowerCase();
        return library.getCatalogue().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Filters books by genre
     *
     * @param genre Genre to filter by
     * @return List of books with the specified genre
     */
    public List<Book> filterByGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            return library.getCatalogue();
        }

        return library.getCatalogue().stream()
                .filter(book -> book.getGenres().stream().anyMatch(g -> g.equals(genre)))
                .collect(Collectors.toList());
    }

    /**
     * Filters books by status
     *
     * @param status Status to filter by
     * @return List of books with the specified status
     */
    public List<Book> filterByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return library.getCatalogue();
        }

        return library.getCatalogue().stream()
                .filter(book -> book.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    /**
     * Filters books by type/class
     *
     * @param type Book type to filter by
     * @return List of books of the specified type
     */
    public List<Book> filterByType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return library.getCatalogue();
        }

        return library.getCatalogue().stream()
                .filter(book -> book.getClass().getSimpleName().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    /**
     * Performs an advanced search using all currently set criteria
     *
     * @return List of books matching all criteria
     */
    public List<Book> advancedSearch() {
        List<Book> results = new ArrayList<>(library.getCatalogue());

        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchTerm = keyword.toLowerCase();
            results = results.stream()
                    .filter(book ->
                            book.getTitle().toLowerCase().contains(searchTerm) ||
                                    (book.getDescription() != null &&
                                            book.getDescription().toLowerCase().contains(searchTerm))
                    )
                    .collect(Collectors.toList());
        }

        if (!genres.isEmpty()) {
            results = results.stream()
                    .filter(book -> book.getGenres().stream().anyMatch(genres::contains))
                    .collect(Collectors.toList());
        }

        if (!status.isEmpty()) {
            results = results.stream()
                    .filter(book -> status.contains(book.getStatus()))
                    .collect(Collectors.toList());
        }

        return results;
    }

    /**
     * Searches for text within a specific book's chapters
     *
     * @param bookId ID of the book to search in
     * @param searchText Text to search for
     * @return List of chapters containing the search text
     */
    public List<Chapter> searchInBookContent(int bookId, String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            Book book = library.getBookById(bookId);
            String searchTerm = searchText.toLowerCase();

            return book.getChapters().stream()
                    .filter(chapter -> {
                        if (chapter instanceof TextChapter) {
                            TextChapter textChapter = (TextChapter) chapter;
                            return textChapter.getContentText() != null &&
                                    textChapter.getContentText().toLowerCase().contains(searchTerm);
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        } catch (BookNotFoundException e) {
            System.err.println("Error searching in book content: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}