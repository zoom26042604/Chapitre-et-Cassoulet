package main.java.fr.ynov.chapitre_et_cassoulet.exception;

/**
 * Exception thrown when file operations fail.
 * Used as a base class for more specific file-related exceptions in the application.
 */
public class FileOperationException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new file operation exception with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception
     */
    public FileOperationException(String message) {
        super(message);
    }

    /**
     * Constructs a new file operation exception with the specified detail message and cause.
     *
     * @param message The detail message explaining the reason for the exception
     * @param cause The underlying cause of this exception
     */
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}