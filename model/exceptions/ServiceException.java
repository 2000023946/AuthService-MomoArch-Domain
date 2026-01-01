package com.authservice.domain.model.exceptions;

/**
 * Generic service exception for unexpected server errors.
 * <p>
 * This is an unchecked exception that extends {@link RuntimeException}.
 * It can be used to wrap unexpected exceptions that occur in the service layer.
 * </p>
 */
public class ServiceException extends RuntimeException {

    /**
     * Constructs a new {@code ServiceException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ServiceException} with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining why the exception was thrown
     * @param cause   the cause of this exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
