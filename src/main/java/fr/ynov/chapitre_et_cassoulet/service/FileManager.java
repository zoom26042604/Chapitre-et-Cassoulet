package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.FileOperationException;
import main.java.fr.ynov.chapitre_et_cassoulet.utils.BookConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Manages file operations for the application including reading and writing files.
 * Handles serialization and deserialization of objects.
 */
public class FileManager {

    /**
     * Default constructor
     */
    public FileManager() {
        initializeDirectories();
    }

    /**
     * Creates necessary directories for application data if they don't exist
     */
    private void initializeDirectories() {
        try {
            createDirectoryIfNotExists(BookConstants.DATA_DIR);
            createDirectoryIfNotExists(BookConstants.BOOKS_DIR);
            createDirectoryIfNotExists(BookConstants.USERS_DIR);
        } catch (IOException e) {
            System.err.println("Error creating application directories: " + e.getMessage());
        }
    }

    /**
     * Creates a directory if it doesn't already exist
     *
     * @param directoryPath Path of the directory to create
     * @throws IOException If directory creation fails
     */
    private void createDirectoryIfNotExists(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Reads text from a file
     *
     * @param filePath Path to the file to read
     * @return String containing the file contents
     * @throws IOException If reading the file fails
     */
    public String readTextFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    /**
     * Writes text to a file
     *
     * @param filePath Path to the file to write
     * @param content Text content to write to the file
     * @throws IOException If writing to the file fails
     */
    public void writeTextFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Deserializes an object from a file
     *
     * @param filePath Path of the file to read from
     * @return The deserialized object
     * @throws FileOperationException If deserialization or file reading fails
     */
    public Object loadObject(String filePath) throws FileOperationException {
        try {
            try (ObjectInputStream in = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(filePath)))) {
                return in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new FileOperationException("Failed to load object: " + e.getMessage(), e);
        }
    }

    /**
     * Loads the library from file or creates a new one if loading fails
     *
     * @return The loaded Library object
     * @throws FileOperationException If loading fails and it's not because the file doesn't exist
     */
    public Library loadLibrary() throws FileOperationException {
        String libraryFile = BookConstants.DATA_DIR + "library.dat";
        File file = new File(libraryFile);

        if (file.exists()) {
            try {
                return (Library) loadObject(libraryFile);
            } catch (FileOperationException e) {
                throw new FileOperationException("Failed to load library: " + e.getMessage(), e);
            }
        } else {
            return new Library();
        }
    }
}



