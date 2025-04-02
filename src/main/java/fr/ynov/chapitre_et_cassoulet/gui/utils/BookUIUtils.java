package main.java.fr.ynov.chapitre_et_cassoulet.gui.utils;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.BookNotFoundException;
import main.java.fr.ynov.chapitre_et_cassoulet.exception.ChapterContentException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Chapter;
import main.java.fr.ynov.chapitre_et_cassoulet.model.TextChapter;
import main.java.fr.ynov.chapitre_et_cassoulet.service.Library;
import main.java.fr.ynov.chapitre_et_cassoulet.service.Search;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.panels.BookCataloguePanel;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.panels.BookDetailsPanel;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.panels.BookSearchPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for book-related operations in the UI
 */
public class BookUIUtils {

    /**
     * Displays details of the currently selected book
     */
    public static void displayBookDetails(JFrame parentFrame, BookCataloguePanel cataloguePanel,
                                          BookDetailsPanel detailsPanel, Library library) {
        int bookId = cataloguePanel.getSelectedBookId();
        if (bookId == -1) {
            detailsPanel.clearDetails();
            return;
        }

        try {
            Book book = library.getBookById(bookId);
            detailsPanel.displayBook(book);
        } catch (BookNotFoundException e) {
            JOptionPane.showMessageDialog(parentFrame, "Error loading book details: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens a dialog showing the content of the selected chapter for reading
     *
     * @param parentFrame The parent frame for the dialog
     * @param book The book containing the chapter
     * @param chapter The chapter to read
     */
    public static void openChapterReader(JFrame parentFrame, Book book, Chapter chapter) {
        JDialog readerDialog = new JDialog(parentFrame, "Reading: " + chapter.getTitle(), true);
        readerDialog.setLayout(new BorderLayout(10, 10));
        readerDialog.setSize(900, 700);
        readerDialog.setLocationRelativeTo(parentFrame);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        headerPanel.setBackground(new Color(245, 245, 245));

        JLabel bookInfoLabel = new JLabel(book.getTitle() + " - Chapter " + chapter.getNumOrder() + ": " + chapter.getTitle());
        bookInfoLabel.setFont(new Font("Serif", Font.BOLD, 18));
        headerPanel.add(bookInfoLabel, BorderLayout.CENTER);

        JTextArea contentArea = new JTextArea();

        try {
            if (chapter instanceof TextChapter) {
                TextChapter textChapter = (TextChapter) chapter;
                textChapter.validateContent();
                contentArea.setText(textChapter.getContentText());
            } else {
                contentArea.setText("This chapter format is not supported for reading.");
            }
        } catch (ChapterContentException e) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Error loading chapter content: " + e.getMessage(),
                    "Chapter Content Error", JOptionPane.ERROR_MESSAGE);
            contentArea.setText("Unable to load chapter content. The content may be missing or corrupted.");
        }

        contentArea.setEditable(false);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setFont(new Font("Serif", Font.PLAIN, 16));
        contentArea.setMargin(new Insets(25, 30, 25, 30));
        contentArea.setBackground(new Color(252, 252, 250));

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(0);
        });

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navigationPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        navigationPanel.setBackground(new Color(245, 245, 245));

        JButton prevButton = new JButton("« Previous Chapter");
        JButton closeButton = new JButton("Close");
        JButton nextButton = new JButton("Next Chapter »");

        Font buttonFont = new Font("Dialog", Font.PLAIN, 14);
        prevButton.setFont(buttonFont);
        closeButton.setFont(buttonFont);
        nextButton.setFont(buttonFont);

        navigationPanel.add(prevButton);
        navigationPanel.add(closeButton);
        navigationPanel.add(nextButton);

        List<Chapter> sortedChapters = new java.util.ArrayList<>(book.getChapters());
        sortedChapters.sort(Comparator.comparingInt(Chapter::getNumOrder));
        int currentChapterIndex = sortedChapters.indexOf(chapter);

        prevButton.setEnabled(currentChapterIndex > 0);
        nextButton.setEnabled(currentChapterIndex < sortedChapters.size() - 1);

        prevButton.addActionListener(e -> {
            readerDialog.dispose();
            if (currentChapterIndex > 0) {
                openChapterReader(parentFrame, book, sortedChapters.get(currentChapterIndex - 1));
            }
        });

        nextButton.addActionListener(e -> {
            readerDialog.dispose();
            if (currentChapterIndex < sortedChapters.size() - 1) {
                openChapterReader(parentFrame, book, sortedChapters.get(currentChapterIndex + 1));
            }
        });

        closeButton.addActionListener(e -> readerDialog.dispose());

        readerDialog.add(headerPanel, BorderLayout.NORTH);
        readerDialog.add(scrollPane, BorderLayout.CENTER);
        readerDialog.add(navigationPanel, BorderLayout.SOUTH);

        readerDialog.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                contentArea.setCaretPosition(0);
            }
        });

        readerDialog.setVisible(true);
    }

    /**
     * Performs search based on criteria selected in the UI
     */
    public static void performSearch(BookSearchPanel searchPanel, BookCataloguePanel cataloguePanel,
                                     Search searchService) {
        String keyword = searchPanel.getSearchKeyword();
        searchService.setKeyword(keyword);

        String genre = searchPanel.getSelectedGenre();
        if (!genre.equals("All Genres")) {
            searchService.clearGenres();
            searchService.addGenre(genre);
        } else {
            searchService.clearGenres();
        }

        String status = searchPanel.getSelectedStatus();
        if (!status.equals("All Statuses")) {
            searchService.clearStatus();
            searchService.addStatus(status);
        } else {
            searchService.clearStatus();
        }

        List<Book> results = searchService.advancedSearch();

        String type = searchPanel.getSelectedType();
        if (!type.equals("All Types")) {
            results = results.stream()
                    .filter(book -> book.getClass().getSimpleName().equals(type))
                    .collect(Collectors.toList());
        }

        cataloguePanel.updateBooks(results);
    }

    /**
     * Resets all search filters and shows all books
     */
    public static void resetSearch(BookSearchPanel searchPanel, BookCataloguePanel cataloguePanel,
                                   Library library) {
        searchPanel.resetFilters();
        cataloguePanel.updateBooks(library.getCatalogue());
    }
}