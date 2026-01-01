package com.authservice.domain.exceptions;

/**
 * Exception thrown when a required key is absent from a mapping.
 * <p>
 * Typically used by {@link com.authservice.infrastructure.mapping.SafeMap} when
 * a key that is expected to exist (e.g., "userId" or "tokenId") is missing.
 * </p>
 *
 * <p>
 * This exception contains the missing key for precise debugging and logging.
 * </p>
 */
public class MissingKeyException extends MappingException {

    /**
     * The key that was not found in the mapping.
     */
    private final String key;

    /**
     * Constructs a MissingKeyException for the specified key.
     *
     * @param key the missing key that caused the exception; must not be null
     */
    public MissingKeyException(String key) {
        super(String.format("Required key '%s' is missing from the input data.", key));
        this.key = key;
    }

    /**
     * Retrieves the key that was missing.
     *
     * @return the missing key as a string
     */
    public String getKey() {
        return key;
    }
}
