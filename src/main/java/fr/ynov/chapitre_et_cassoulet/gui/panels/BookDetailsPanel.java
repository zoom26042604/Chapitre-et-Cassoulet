package main.java.fr.ynov.chapitre_et_cassoulet.gui.panels;

import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Chapter;

import javax.swing.*;
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
import javax.imageio.ImageIO;

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

    /**
     * Constructor for the book details panel
     *
     * @param chapterClickListener Handler for chapter double-click events
     */
    public BookDetailsPanel(BiConsumer<Book, Chapter> chapterClickListener) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Book Details")
        ));

        JPanel coverPanel = new JPanel(new BorderLayout());
        coverPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 15));

        bookCover = new JLabel();
        bookCover.setPreferredSize(new Dimension(180, 240));
        bookCover.setHorizontalAlignment(SwingConstants.CENTER);
        bookCover.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        bookCover.setBackground(Color.WHITE);
        bookCover.setOpaque(true);
        bookCover.setText("No Cover");
        bookCover.setFont(new Font("Dialog", Font.ITALIC, 12));

        coverPanel.add(bookCover, BorderLayout.CENTER);
        add(coverPanel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Font labelFont = new Font("Dialog", Font.BOLD, 12);
        Font contentFont = new Font("Dialog", Font.PLAIN, 12);

        bookTitle = new JLabel("Title: ");
        bookTitle.setFont(labelFont);
        bookTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(bookTitle);
        infoPanel.add(Box.createVerticalStrut(8));

        bookAuthor = new JLabel("Author/Artist: ");
        bookAuthor.setFont(labelFont);
        bookAuthor.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(bookAuthor);
        infoPanel.add(Box.createVerticalStrut(8));

        bookType = new JLabel("Type: ");
        bookType.setFont(labelFont);
        bookType.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(bookType);
        infoPanel.add(Box.createVerticalStrut(8));

        bookStatus = new JLabel("Status: ");
        bookStatus.setFont(labelFont);
        bookStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(bookStatus);
        infoPanel.add(Box.createVerticalStrut(12));

        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(labelFont);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(descLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        bookDescription = new JTextArea(5, 20);
        bookDescription.setEditable(false);
        bookDescription.setLineWrap(true);
        bookDescription.setWrapStyleWord(true);
        bookDescription.setFont(contentFont);
        bookDescription.setBackground(new Color(250, 250, 250));
        JScrollPane descScroll = new JScrollPane(bookDescription);
        descScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        descScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        infoPanel.add(descScroll);
        infoPanel.add(Box.createVerticalStrut(12));

        JLabel genresLabel = new JLabel("Genres:");
        genresLabel.setFont(labelFont);
        genresLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(genresLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        genresList = new JList<>();
        genresList.setFont(contentFont);
        genresList.setVisibleRowCount(3);
        genresList.setBackground(new Color(250, 250, 250));
        JScrollPane genresScroll = new JScrollPane(genresList);
        genresScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        genresScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        infoPanel.add(genresScroll);
        infoPanel.add(Box.createVerticalStrut(12));

        JLabel chaptersLabel = new JLabel("Chapters:");
        chaptersLabel.setFont(labelFont);
        chaptersLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(chaptersLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        chaptersList = new JList<>();
        chaptersList.setFont(contentFont);
        chaptersList.setVisibleRowCount(6);
        chaptersList.setBackground(new Color(250, 250, 250));
        JScrollPane chaptersScroll = new JScrollPane(chaptersList);
        chaptersScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        chaptersScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        infoPanel.add(chaptersScroll);

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

        add(infoPanel, BorderLayout.CENTER);
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

        if (coverPath != null && !coverPath.isEmpty()) {
            try {
                String resourcePath = null;

                String bookType = book.getClass().getSimpleName().toLowerCase();
                String bookDir = book.getTitle().replaceAll("\\s+", "").toLowerCase();
                String bookName = book.getTitle().replace(" ", " ");
                String authorName = book.getArtist() != null ? book.getArtist() : "Unknown";

                resourcePath = "data/books/" + coverPath.substring(coverPath.indexOf("books/") + 6);

                if (getClass().getClassLoader().getResourceAsStream(resourcePath) == null) {
                    resourcePath = "data/books/" + bookType + "s/" + bookDir + "/" +
                            bookDir + "/" + bookName + " - " + authorName + ".jpg";
                }

                if (getClass().getClassLoader().getResourceAsStream(resourcePath) == null) {
                    resourcePath = "data/books/" + bookType + "s/" + bookDir +
                            "/information/coverImage/cover-image.jpg";
                }

                java.io.InputStream imageStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

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
                bookCover.setText("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            bookCover.setIcon(null);
            bookCover.setText("No Cover");
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