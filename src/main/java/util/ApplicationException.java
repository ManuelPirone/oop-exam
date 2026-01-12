package main.java.util;

/**
 * Custom unchecked exception used for Exception Shielding.
 * This class wraps low-level technical exceptions (like IOException or 
 * NumberFormatException) into a high-level domain exception. 
 * This prevents sensitive stack trace information from being exposed 
 * to the end-user while providing meaningful error messages.
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
