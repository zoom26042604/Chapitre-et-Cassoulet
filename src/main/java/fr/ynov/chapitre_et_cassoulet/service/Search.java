package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class providing search and filtering capabilities for the library system.
 */
public class Search {
    private String keyword;
    private List<String> genres;
    private List<String> status;
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
     * Sets the search keyword
     *
     * @param keyword The keyword to search for
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
}