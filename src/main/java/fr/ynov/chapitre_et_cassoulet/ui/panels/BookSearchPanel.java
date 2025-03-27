package main.java.fr.ynov.chapitre_et_cassoulet.ui.panels;

import main.java.fr.ynov.chapitre_et_cassoulet.service.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BookSearchPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> genreFilter;
    private JComboBox<String> statusFilter;
    private JComboBox<String> typeFilter;
    private JButton searchButton;
    private JButton resetButton;

    /**
     * Constructor for the search panel
     *
     * @param genres List of available genres from the library
     * @param statuses List of available statuses from the library
     * @param searchListener Action listener for the search button
     * @param resetListener Action listener for the reset button
     */
    public BookSearchPanel(Library library, ActionListener searchListener, ActionListener resetListener) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        add(new JLabel("Search:"));
        searchField = new JTextField(20);
        add(searchField);

        add(new JLabel("Genre:"));
        genreFilter = new JComboBox<>();
        genreFilter.setPreferredSize(new Dimension(120, 25));
        genreFilter.addItem("All Genres");
        for (String genre : library.getAllGenres()) {
            genreFilter.addItem(genre);
        }
        add(genreFilter);

        add(new JLabel("Status:"));
        statusFilter = new JComboBox<>();
        statusFilter.setPreferredSize(new Dimension(100, 25));
        statusFilter.addItem("All Statuses");
        for (String status : library.getAllStatuses()) {
            statusFilter.addItem(status);
        }
        add(statusFilter);

        add(new JLabel("Type:"));
        typeFilter = new JComboBox<>();
        typeFilter.setPreferredSize(new Dimension(100, 25));
        typeFilter.addItem("All Types");
        typeFilter.addItem("Novel");
        typeFilter.addItem("Manga");
        typeFilter.addItem("Roman");
        add(typeFilter);

        searchButton = new JButton("Search");
        searchButton.addActionListener(searchListener);
        add(searchButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(resetListener);
        add(resetButton);
    }

    /**
     * Gets the search keyword
     *
     * @return The text from the search field
     */
    public String getSearchKeyword() {
        return searchField.getText();
    }

    /**
     * Gets the selected genre
     *
     * @return The selected genre as a string
     */
    public String getSelectedGenre() {
        return genreFilter.getSelectedItem().toString();
    }

    /**
     * Gets the selected status
     *
     * @return The selected status as a string
     */
    public String getSelectedStatus() {
        return statusFilter.getSelectedItem().toString();
    }

    /**
     * Gets the selected book type
     *
     * @return The selected type as a string
     */
    public String getSelectedType() {
        return typeFilter.getSelectedItem().toString();
    }

    /**
     * Resets all search filters
     */
    public void resetFilters() {
        searchField.setText("");
        genreFilter.setSelectedIndex(0);
        statusFilter.setSelectedIndex(0);
        typeFilter.setSelectedIndex(0);
    }
}