package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when authentication fails due to invalid credentials.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to indicate that a user has provided incorrect login
 * credentials such as username/email or password.
 * </p>
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidCredentialsException} with the specified
     * detail message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
