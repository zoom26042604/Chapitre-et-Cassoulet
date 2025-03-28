package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.FileOperationException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.*;
import main.java.fr.ynov.chapitre_et_cassoulet.gui.Interface;

import javax.swing.*;
import java.io.File;

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
            FileManager fileManager = new FileManager();
            String resourcesPath = fileManager.getResourcesPath();
            String booksRootPath = resourcesPath + File.separator + "data" + File.separator + "books";

            File booksRoot = new File(booksRootPath);
            System.out.println("Looking for books in: " + booksRoot.getAbsolutePath());

            if (!booksRoot.exists() || !booksRoot.isDirectory()) {
                System.err.println("Books directory not found: " + booksRoot.getAbsolutePath());
                return;
            }

            File[] bookTypeDirectories = booksRoot.listFiles(File::isDirectory);
            if (bookTypeDirectories == null) {
                System.err.println("No book type directories found");
                return;
            }

            System.out.println("Found " + bookTypeDirectories.length + " book type directories");

            for (File bookTypeDir : bookTypeDirectories) {
                System.out.println("Processing book type directory: " + bookTypeDir.getName());
                File[] bookSeriesDirs = bookTypeDir.listFiles(File::isDirectory);
                if (bookSeriesDirs == null) continue;

                System.out.println("Found " + bookSeriesDirs.length + " book series directories in " + bookTypeDir.getName());
                for (File seriesDir : bookSeriesDirs) {
                    System.out.println("Processing series directory: " + seriesDir.getName());
                    File infoDir = new File(seriesDir, "information");
                    if (!infoDir.exists() || !infoDir.isDirectory()) {
                        System.out.println("No information directory found in: " + seriesDir.getAbsolutePath());
                        continue;
                    }

                    File[] infoFiles = infoDir.listFiles((dir, name) -> name.endsWith(".json"));
                    if (infoFiles == null || infoFiles.length == 0) {
                        System.out.println("No info files found in: " + infoDir.getAbsolutePath());
                        continue;
                    }

                    System.out.println("Loading book from: " + infoFiles[0].getAbsolutePath());
                    Book book = dataLoader.loadBookFromFile(infoFiles[0].getPath());

                    File chaptersDir = new File(seriesDir, "chapters");
                    if (chaptersDir.exists() && chaptersDir.isDirectory()) {
                        File[] chapterFiles = chaptersDir.listFiles((dir, name) -> name.endsWith(".json"));
                        if (chapterFiles != null) {
                            System.out.println("Found " + chapterFiles.length + " chapter files");
                            for (File chapterFile : chapterFiles) {
                                try {
                                    TextChapter chapter = dataLoader.loadChapterFromFile(chapterFile.getPath());
                                    book.addChapter(chapter);
                                    // Removed the individual chapter logging
                                } catch (FileOperationException e) {
                                    System.err.println("Error loading chapter from " + chapterFile.getPath() + ": " + e.getMessage());
                                }
                            }
                        }
                    }

                    library.addBook(book);
                    System.out.println("Added book to library: " + book.getTitle());
                }
            }

            System.out.println("Total books loaded: " + library.getCatalogue().size());
        } catch (Exception e) {
            System.err.println("Error loading from JSON structure: " + e.getMessage());
            e.printStackTrace();
        }
    }

}