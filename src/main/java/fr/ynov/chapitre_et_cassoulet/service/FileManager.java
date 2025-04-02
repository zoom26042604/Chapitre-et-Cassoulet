package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.FileOperationException;

import java.io.*;

/**
 * Handles file operations for the application, including saving and loading
 * the library data.
 */
public class FileManager {
    private static final String LIBRARY_FILE = "library.dat";
    private static final String DATA_DIRECTORY = "src" + File.separator + "resources" + File.separator + "data";

    /**
     * Loads the library from a file
     *
     * @return The loaded library
     * @throws FileOperationException If the library cannot be loaded
     */
    public Library loadLibrary() throws FileOperationException {
        File file = new File(LIBRARY_FILE);

        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                Library library = (Library) in.readObject();
                System.out.println("Library loaded successfully from " + LIBRARY_FILE);
                System.out.println("Loaded " + library.getCatalogue().size() + " books");
                return library;
            } catch (IOException | ClassNotFoundException e) {
                throw new FileOperationException("Failed to load library: " + e.getMessage(), e);
            }
        } else {
            throw new FileOperationException("Library file not found: " + LIBRARY_FILE);
        }
    }

    /**
     * Gets the absolute path of the resources directory
     *
     * @return The absolute path to the resources directory
     */
    public String getResourcesPath() {
        File resourcesDir = new File("src/resources");
        if (!resourcesDir.exists()) {
            resourcesDir = new File("../src/resources");
            if (!resourcesDir.exists()) {
                resourcesDir = new File(System.getProperty("user.dir"), "src/resources");
                if (!resourcesDir.exists()) {
                    System.err.println("Could not locate resources directory");
                }
            }
        }
        return resourcesDir.getAbsolutePath();
    }
}