package main.java.fr.ynov.chapitre_et_cassoulet.exception;

/**
 * Exception thrown when content cannot be loaded for a chapter.
 * Extends FileOperationException to maintain hierarchy.
 */
public class ChapterContentException extends FileOperationException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new chapter content exception with the specified detail message
     *
     * @param message The detail message
     */
    public ChapterContentException(String message) {
        super(message);
    }

    /**
     * Constructs a new chapter content exception with the specified detail message and cause
     *
     * @param message The detail message
     * @param cause The cause of the exception
     */
    public ChapterContentException(String message, Throwable cause) {
        super(message, cause);
    }
}