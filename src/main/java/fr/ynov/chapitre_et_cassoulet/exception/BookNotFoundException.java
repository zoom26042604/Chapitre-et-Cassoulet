package main.java.fr.ynov.chapitre_et_cassoulet.exception;

import java.io.Serial;

/**
 * Exception thrown when a requested book cannot be found
 */
public class BookNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new book not found exception with the specified detail message
     *
     * @param message The detail message explaining why the book was not found
     */
    public BookNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new book not found exception with the specified detail message and cause
     *
     * @param message The detail message explaining why the book was not found
     * @param cause The underlying cause of this exception
     */
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}