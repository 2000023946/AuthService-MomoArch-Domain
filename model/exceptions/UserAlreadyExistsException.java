package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when an attempt is made to register or create a user
 * that already exists in the system.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to signal that a user with the specified identifier
 * (e.g., email or username) already exists.
 * </p>
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code UserAlreadyExistsException} with the specified detail
     * message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
