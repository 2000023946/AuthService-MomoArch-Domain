package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when an invalid Multi-Factor Authentication (MFA) code is
 * provided.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to signal authentication failure due to an incorrect MFA code.
 * </p>
 */
public class InvalidMfaCodeException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidMfaCodeException} with the specified detail
     * message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public InvalidMfaCodeException(String message) {
        super(message);
    }
}
