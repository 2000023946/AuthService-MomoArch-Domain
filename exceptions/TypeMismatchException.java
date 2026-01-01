package com.authservice.domain.exceptions;

/**
 * Exception thrown when a value associated with a key cannot be converted to
 * the expected type.
 * <p>
 * Typically used by {@link com.authservice.infrastructure.mapping.SafeMap} when
 * a key exists
 * but its value cannot satisfy the requested type (e.g., trying to get an int
 * from a String
 * that is not numeric).
 * </p>
 *
 * <p>
 * This exception contains the key and the expected type for precise debugging,
 * allowing
 * logs to indicate exactly where the mapping failed.
 * </p>
 */
public class TypeMismatchException extends MappingException {

    /**
     * The key whose value could not be converted to the expected type.
     */
    private final String key;

    /**
     * The type that was expected for the value of the key.
     */
    private final String expectedType;

    /**
     * Constructs a TypeMismatchException for a key and its expected type.
     *
     * @param key          the key whose value is incompatible; must not be null
     * @param expectedType the type that was expected for the key; must not be null
     */
    public TypeMismatchException(String key, String expectedType) {
        super(String.format("Key '%s' expected type %s but found an incompatible type.", key, expectedType));
        this.key = key;
        this.expectedType = expectedType;
    }

    /**
     * Retrieves the key that caused the type mismatch.
     *
     * @return the key as a string
     */
    public String getKey() {
        return key;
    }

    /**
     * Retrieves the expected type of the value for the key.
     *
     * @return the expected type as a string
     */
    public String getExpectedType() {
        return expectedType;
    }
}
