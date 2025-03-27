package main.java.fr.ynov.chapitre_et_cassoulet.gui.panels;

import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookCataloguePanel extends JPanel {
    private JTable bookTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor for the catalogue panel
     *
     * @param selectionListener Listener for book selection events
     */
    public BookCataloguePanel(ListSelectionListener selectionListener) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Book Catalogue"));

        String[] columnNames = {"ID", "Title", "Type", "Status", "Chapters"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookTable.getSelectionModel().addListSelectionListener(selectionListener);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Updates the table with books
     *
     * @param books List of books to display
     */
    public void updateBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getClass().getSimpleName(),
                    book.getStatus(),
                    book.getChapters().size()
            });
        }
    }

    /**
     * Gets the ID of the selected book
     *
     * @return ID of the selected book or -1 if no selection
     */
    public int getSelectedBookId() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (int) tableModel.getValueAt(selectedRow, 0);
    }
}