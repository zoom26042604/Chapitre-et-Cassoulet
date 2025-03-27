package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.FileOperationException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.*;
import main.java.fr.ynov.chapitre_et_cassoulet.ui.Interface;
import main.java.fr.ynov.chapitre_et_cassoulet.utils.BookConstants;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class ApplicationLauncher {

    public void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set UI look and feel: " + e.getMessage());
        }

        Library library = loadOrCreateLibrary();

        if (library.getCatalogue().isEmpty()) {
            addSampleData(library);
        }

        SwingUtilities.invokeLater(() -> {
            Interface ui = new Interface(library);
            ui.setVisible(true);
        });
    }

    private Library loadOrCreateLibrary() {
        FileManager fileManager = new FileManager();
        try {
            return fileManager.loadLibrary();
        } catch (FileOperationException e) {
            System.out.println("Creating new library: " + e.getMessage());
            return new Library();
        }
    }

    private void addSampleData(Library library) {
        try {
            DataLoader dataLoader = new DataLoader();

            loadFromJsonStructure(library, dataLoader);

            System.out.println("Total books loaded: " + library.getCatalogue().size());
        } catch (Exception e) {
            System.err.println("Failed to load sample data: " + e.getMessage());
        }
    }

    private void loadFromJsonStructure(Library library, DataLoader dataLoader) {
        try {
            String booksRootPath = "src/resources/data/books";
            File booksRoot = new File(booksRootPath);

            File[] bookTypeDirectories = booksRoot.listFiles(File::isDirectory);
            if (bookTypeDirectories == null) {
                return;
            }

            for (File bookTypeDir : bookTypeDirectories) {
                File[] bookSeriesDirs = bookTypeDir.listFiles(File::isDirectory);
                if (bookSeriesDirs == null) continue;

                for (File seriesDir : bookSeriesDirs) {
                    File infoDir = new File(seriesDir, "information");
                    if (!infoDir.exists() || !infoDir.isDirectory()) continue;

                    File[] infoFiles = infoDir.listFiles((dir, name) -> name.endsWith(".json"));
                    if (infoFiles == null || infoFiles.length == 0) continue;

                    Book book = dataLoader.loadBookFromFile(infoFiles[0].getPath());

                    File chaptersDir = new File(seriesDir, "chapters");
                    if (chaptersDir.exists() && chaptersDir.isDirectory()) {
                        File[] chapterFiles = chaptersDir.listFiles((dir, name) -> name.endsWith(".json"));
                        if (chapterFiles != null) {
                            for (File chapterFile : chapterFiles) {
                                try {
                                    TextChapter chapter = dataLoader.loadChapterFromFile(chapterFile.getPath());
                                    book.addChapter(chapter);
                                } catch (FileOperationException e) {
                                    System.err.println("Error loading chapter from " + chapterFile.getPath());
                                }
                            }
                        }
                    }

                    library.addBook(book);
                    System.out.println("Loaded book: " + book.getTitle() + " from JSON");
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading from JSON structure: " + e.getMessage());
        }
    }

}