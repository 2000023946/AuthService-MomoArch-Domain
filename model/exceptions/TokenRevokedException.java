package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when a token has been revoked and can no longer be used.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to indicate that a token was previously valid but has
 * since been revoked by the server or administrator.
 * </p>
 */
public class TokenRevokedException extends RuntimeException {

    /**
     * Constructs a new {@code TokenRevokedException} with the specified detail
     * message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public TokenRevokedException(String message) {
        super(message);
    }
}
