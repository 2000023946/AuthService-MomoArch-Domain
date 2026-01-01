package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when a user is not found in the system.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to signal that a requested user resource does not exist.
 * </p>
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code UserNotFoundException} with the specified detail
     * message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
