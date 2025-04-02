package main.java.fr.ynov.chapitre_et_cassoulet.gui.panels;

import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Chapter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

public class BookDetailsPanel extends JPanel {
    private JLabel bookCover;
    private JLabel bookTitle;
    private JLabel bookAuthor;
    private JLabel bookType;
    private JLabel bookStatus;
    private JTextArea bookDescription;
    private JList<String> genresList;
    private JList<String> chaptersList;
    private List<Chapter> sortedChapters;

    private final Color PANEL_BACKGROUND = new Color(252, 252, 252);
    private final Color HEADER_COLOR = new Color(80, 80, 120);
    private final Color BORDER_COLOR = new Color(220, 220, 220);
    private final Color SECTION_BACKGROUND = new Color(245, 245, 250);

    /**
     * Constructor for the book details panel
     *
     * @param chapterClickListener Handler for chapter double-click events
     */
    public BookDetailsPanel(BiConsumer<Book, Chapter> chapterClickListener) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(PANEL_BACKGROUND);

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(PANEL_BACKGROUND);

        JPanel headerPanel = createHeaderPanel();
        mainContainer.add(headerPanel);
        mainContainer.add(Box.createVerticalStrut(10));

        JPanel descriptionPanel = createDescriptionPanel();
        mainContainer.add(descriptionPanel);
        mainContainer.add(Box.createVerticalStrut(10));

        JPanel genresPanel = createGenresPanel();
        mainContainer.add(genresPanel);
        mainContainer.add(Box.createVerticalStrut(10));

        JPanel chaptersPanel = createChaptersPanel();
        setupChaptersListeners(chapterClickListener);
        mainContainer.add(chaptersPanel);

        add(mainContainer, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBackground(SECTION_BACKGROUND);
        headerPanel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        "Book Overview",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Dialog", Font.BOLD, 12),
                        HEADER_COLOR
                ),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JPanel coverPanel = new JPanel(new BorderLayout());
        coverPanel.setBackground(SECTION_BACKGROUND);

        bookCover = new JLabel();
        bookCover.setPreferredSize(new Dimension(160, 220));
        bookCover.setHorizontalAlignment(SwingConstants.CENTER);
        bookCover.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2),
                BorderFactory.createLineBorder(BORDER_COLOR, 1)
        ));
        bookCover.setBackground(Color.WHITE);
        bookCover.setOpaque(true);
        bookCover.setText("No Cover");
        bookCover.setFont(new Font("Dialog", Font.ITALIC, 12));

        coverPanel.add(bookCover, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(SECTION_BACKGROUND);
        infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        Font labelFont = new Font("Dialog", Font.BOLD, 13);

        bookTitle = new JLabel("Title: ");
        bookTitle.setFont(new Font("Dialog", Font.BOLD, 15));
        bookTitle.setForeground(new Color(50, 50, 100));
        bookTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        bookAuthor = new JLabel("Author/Artist: ");
        bookAuthor.setFont(labelFont);
        bookAuthor.setForeground(new Color(50, 50, 80));
        bookAuthor.setAlignmentX(Component.LEFT_ALIGNMENT);

        bookType = new JLabel("Type: ");
        bookType.setFont(labelFont);
        bookType.setForeground(new Color(50, 50, 80));
        bookType.setAlignmentX(Component.LEFT_ALIGNMENT);

        bookStatus = new JLabel("Status: ");
        bookStatus.setFont(labelFont);
        bookStatus.setForeground(new Color(50, 50, 80));
        bookStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(bookTitle);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(bookAuthor);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(bookType);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(bookStatus);
        infoPanel.add(Box.createVerticalGlue());

        headerPanel.add(coverPanel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createDescriptionPanel() {
        JPanel descPanel = new JPanel(new BorderLayout(0, 5));
        descPanel.setBackground(SECTION_BACKGROUND);
        descPanel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        "Description",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Dialog", Font.BOLD, 12),
                        HEADER_COLOR
                ),
                new EmptyBorder(8, 10, 8, 10)
        ));

        bookDescription = new JTextArea(4, 20);
        bookDescription.setEditable(false);
        bookDescription.setLineWrap(true);
        bookDescription.setWrapStyleWord(true);
        bookDescription.setFont(new Font("Dialog", Font.PLAIN, 12));
        bookDescription.setBackground(Color.WHITE);
        bookDescription.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane descScroll = new JScrollPane(bookDescription);
        descScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        descPanel.add(descScroll, BorderLayout.CENTER);

        return descPanel;
    }

    private JPanel createGenresPanel() {
        JPanel genresPanel = new JPanel(new BorderLayout(0, 5));
        genresPanel.setBackground(SECTION_BACKGROUND);
        genresPanel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        "Genres",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Dialog", Font.BOLD, 12),
                        HEADER_COLOR
                ),
                new EmptyBorder(8, 10, 8, 10)
        ));

        genresList = new JList<>();
        genresList.setFont(new Font("Dialog", Font.PLAIN, 12));
        genresList.setVisibleRowCount(3);
        genresList.setBackground(Color.WHITE);
        genresList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(new EmptyBorder(2, 5, 2, 5));
                return label;
            }
        });

        JScrollPane genresScroll = new JScrollPane(genresList);
        genresScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        genresPanel.add(genresScroll, BorderLayout.CENTER);

        return genresPanel;
    }

    private JPanel createChaptersPanel() {
        JPanel chaptersPanel = new JPanel(new BorderLayout(0, 5));
        chaptersPanel.setBackground(SECTION_BACKGROUND);
        chaptersPanel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        "Chapters",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Dialog", Font.BOLD, 12),
                        HEADER_COLOR
                ),
                new EmptyBorder(8, 10, 8, 10)
        ));

        chaptersList = new JList<>();
        chaptersList.setFont(new Font("Dialog", Font.PLAIN, 12));
        chaptersList.setVisibleRowCount(6);
        chaptersList.setBackground(Color.WHITE);
        chaptersList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(new EmptyBorder(3, 5, 3, 5));
                return label;
            }
        });

        JScrollPane chaptersScroll = new JScrollPane(chaptersList);
        chaptersScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        chaptersPanel.add(chaptersScroll, BorderLayout.CENTER);

        return chaptersPanel;
    }

    private void setupChaptersListeners(BiConsumer<Book, Chapter> chapterClickListener) {
        chaptersList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedChapterIndex = chaptersList.getSelectedIndex();
                    if (selectedChapterIndex != -1 && sortedChapters != null) {
                        Book currentBook = (Book) chaptersList.getClientProperty("currentBook");
                        if (currentBook != null) {
                            Chapter selectedChapter = sortedChapters.get(selectedChapterIndex);
                            chapterClickListener.accept(currentBook, selectedChapter);
                        }
                    }
                }
            }
        });
    }

    /**
     * Displays the details of a book
     *
     * @param book The book to display
     */
    public void displayBook(Book book) {
        if (book == null) {
            clearDetails();
            return;
        }

        bookTitle.setText("Title: " + book.getTitle());
        bookType.setText("Type: " + book.getType());
        bookAuthor.setText("Author/Artist: " + (book.getArtist() != null ? book.getArtist() : "Unknown"));
        bookStatus.setText("Status: " + book.getStatus());
        bookDescription.setText(book.getDescription() != null ? book.getDescription() : "No description available.");

        loadBookCover(book);

        DefaultListModel<String> genresModel = new DefaultListModel<>();
        for (String genre : book.getGenres()) {
            genresModel.addElement(genre);
        }
        genresList.setModel(genresModel);

        sortedChapters = new java.util.ArrayList<>(book.getChapters());
        sortedChapters.sort(Comparator.comparingInt(Chapter::getNumOrder));

        DefaultListModel<String> chaptersModel = new DefaultListModel<>();
        for (Chapter chapter : sortedChapters) {
            chaptersModel.addElement("Chapter " + chapter.getNumOrder() + ": " + chapter.getTitle());
        }
        chaptersList.setModel(chaptersModel);

        chaptersList.putClientProperty("currentBook", book);
    }

    /**
     * Loads and displays the cover image for a book
     *
     * @param book The book whose cover to display
     */
    private void loadBookCover(Book book) {
        String coverPath = book.getCoverImagePath();

        try {
            String normalizedTitle = book.getTitle()
                    .toLowerCase()
                    .replaceAll("[\\s'']", "")
                    .replaceAll("[àáâãäå]", "a")
                    .replaceAll("[èéêë]", "e")
                    .replaceAll("[ìíîï]", "i")
                    .replaceAll("[òóôõö]", "o")
                    .replaceAll("[ùúûü]", "u");

            String bookType = book.getClass().getSimpleName().toLowerCase();
            String typePlural = bookType.equals("roman") ? "romans" : bookType + "s";

            String[] possiblePaths = {
                    coverPath,

                    String.format("resources/data/books/%s/%s/information/coverImage/cover-image.jpg",
                            typePlural,
                            normalizedTitle),

                    String.format("src/resources/data/books/%s/%s/information/coverImage/cover-image.jpg",
                            typePlural,
                            normalizedTitle)
            };

            java.io.InputStream imageStream = null;

            for (String path : possiblePaths) {
                if (path == null || path.isEmpty()) continue;

                imageStream = getClass().getClassLoader().getResourceAsStream(path);
                if (imageStream != null) {
                    break;
                }

                java.io.File file = new java.io.File(path);
                if (file.exists()) {
                    imageStream = new java.io.FileInputStream(file);
                    break;
                }
            }

            if (imageStream != null) {
                ImageIcon originalIcon = new ImageIcon(ImageIO.read(imageStream));
                Image image = originalIcon.getImage();

                int labelWidth = bookCover.getPreferredSize().width;
                int labelHeight = bookCover.getPreferredSize().height;

                double widthRatio = (double) labelWidth / originalIcon.getIconWidth();
                double heightRatio = (double) labelHeight / originalIcon.getIconHeight();
                double ratio = Math.min(widthRatio, heightRatio);

                int width = (int) (originalIcon.getIconWidth() * ratio);
                int height = (int) (originalIcon.getIconHeight() * ratio);

                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                bookCover.setIcon(scaledIcon);
                bookCover.setText("");
            } else {
                bookCover.setIcon(null);
                bookCover.setText("No Cover");
            }
        } catch (Exception e) {
            bookCover.setIcon(null);
            bookCover.setText("Error");
            System.err.println("Error loading cover for " + book.getTitle() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clears all details in the panel
     */
    public void clearDetails() {
        bookTitle.setText("Title: ");
        bookAuthor.setText("Author/Artist: ");
        bookType.setText("Type: ");
        bookStatus.setText("Status: ");
        bookDescription.setText("");
        genresList.setModel(new DefaultListModel<>());
        chaptersList.setModel(new DefaultListModel<>());
        bookCover.setIcon(null);
        bookCover.setText("No Cover");
        sortedChapters = null;
    }
}