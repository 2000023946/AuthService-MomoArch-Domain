package com.authservice.domain.model.exceptions;

/**
 * Exception thrown when a user attempts to log in but their account
 * has not been verified yet.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to signal that the user's account is pending verification,
 * such as email confirmation, and cannot be used to access protected resources.
 * </p>
 */
public class UserNotVerifiedException extends RuntimeException {

    /**
     * Constructs a new {@code UserNotVerifiedException} with the specified detail
     * message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public UserNotVerifiedException(String message) {
        super(message);
    }
}
