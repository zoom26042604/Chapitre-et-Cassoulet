package main.java.fr.ynov.chapitre_et_cassoulet.gui;

import main.java.fr.ynov.chapitre_et_cassoulet.service.Library;
import main.java.fr.ynov.chapitre_et_cassoulet.service.Search;
import main.java.fr.ynov.chapitre_et_cassoulet.service.FileManager;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.panels.BookCataloguePanel;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.panels.BookDetailsPanel;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.panels.BookSearchPanel;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.utils.BookUIUtils;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;

/**
 * Main graphical user interface for the library application.
 * Provides the ability to view, search, and filter books in the library.
 */
public class Interface extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Library library;
    private final Search searchService;
    private final FileManager fileManager;

    private JPanel mainPanel;
    private BookSearchPanel searchPanel;
    private BookCataloguePanel cataloguePanel;
    private BookDetailsPanel detailsPanel;

    /**
     * Constructor for the Interface
     *
     * @param library The library to display in the interface
     */
    public Interface(Library library) {
        this.library = library;
        this.searchService = new Search(library);
        this.fileManager = new FileManager();

        setTitle("Chapitre et Cassoulet - Library Application");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadData();
    }

    /**
     * Initializes all UI components
     */
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        searchPanel = new BookSearchPanel(library,
                e -> BookUIUtils.performSearch(searchPanel, cataloguePanel, searchService),
                e -> BookUIUtils.resetSearch(searchPanel, cataloguePanel, library));

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.6);

        cataloguePanel = new BookCataloguePanel(e -> {
            if (!e.getValueIsAdjusting()) {
                BookUIUtils.displayBookDetails(this, cataloguePanel, detailsPanel, library);
            }
        });
        splitPane.setLeftComponent(cataloguePanel);

        detailsPanel = new BookDetailsPanel((book, chapter) ->
                BookUIUtils.openChapterReader(this, book, chapter));

        splitPane.setRightComponent(detailsPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.add(new JLabel("Total books: " + library.getTotalBooks()));
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /**
     * Loads all books from the library into the UI
     */
    private void loadData() {
        cataloguePanel.updateBooks(library.getCatalogue());
    }
}