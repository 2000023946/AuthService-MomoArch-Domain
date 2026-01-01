package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when a provided token is invalid.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to indicate that the token provided by the client
 * does not meet the expected format or verification requirements.
 * </p>
 */
public class InvalidTokenException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidTokenException} with the specified detail
     * message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}
