package com.authservice.domain.exceptions;

/**
 * The base class for all mapping-related exceptions within the domain.
 * <p>
 * Any failure in data extraction, type conversion, or structural integrity
 * from raw mappings should inherit from this class. This allows for
 * centralized exception handling in services that rely on
 * {@link com.authservice.domain.model.aggregates.mapping.interfaces.IMap}.
 * </p>
 *
 * <p>
 * This is an <strong>unchecked exception</strong>, meaning it extends
 * {@link RuntimeException}.
 * This design reflects the philosophy that mapping errors are usually
 * unrecoverable
 * logical or data errors, not something the caller can safely recover from.
 * </p>
 */
public abstract class MappingException extends RuntimeException {

    /**
     * Constructs a new MappingException with the specified detail message.
     *
     * @param message the detailed message explaining the reason for the exception
     */
    protected MappingException(String message) {
        super(message);
    }
}
