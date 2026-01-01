package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when a user exceeds the allowed number of login attempts.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to enforce rate limiting or account lockout policies.
 * </p>
 */
public class TooManyLoginAttemptsException extends RuntimeException {

    /**
     * Constructs a new {@code TooManyLoginAttemptsException} with the specified
     * detail message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public TooManyLoginAttemptsException(String message) {
        super(message);
    }
}
